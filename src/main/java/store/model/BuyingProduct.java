package store.model;

import java.util.List;

import store.constant.ErrorMessage;

public class BuyingProduct {
	private String name;
	private int quantity;
	private int promotionQuantity;
	private int generalQuantity;
	private PromotionStatus promotionStatus;
	private boolean isApplied;

	public BuyingProduct(Products products, String input) {
		validateFormat(input);
		validateInventory(products, input);

		String[] product = input.substring(1, input.length() - 1).split("-");
		this.name = product[0];
		this.quantity = Integer.parseInt(product[1]);
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public PromotionStatus getPromotionStatus() {
		return promotionStatus;
	}

	public boolean getIsApplied() {
		return isApplied;
	}

	public int getPromotionQuantity() {
		return promotionQuantity;
	}

	public int getGeneralQuantity() {
		return generalQuantity;
	}

	public void setFinalQuantity(int promotionQuantity, int generalQuantity) {
		this.promotionQuantity = promotionQuantity;
		this.generalQuantity = generalQuantity;
	}

	public int calculatePromotionQuantity(Products products) {
		Product promotionProduct = products.findPromotionProduct(name);
		if (promotionProduct != null) {
			Promotion promotion = promotionProduct.getPromotion();

			if(promotion.checkUsable() && promotionProduct.getQuantity() > 0) {
				int applyNumber = 0;
				while (applyNumber < quantity && applyNumber + promotion.getUnit() <= promotionProduct.getQuantity()) {
					applyNumber += promotion.getUnit();
				}
				return applyNumber;
			}
		}
		return 0;
	}

	public int calculateGeneralQuantity(int promotionQuantity) {
		return Math.max(0, quantity - promotionQuantity);
	}

	public void applyPromotion(Products products) {
		int promotionQuantity = calculatePromotionQuantity(products);
		int generalQuantity = calculateGeneralQuantity(promotionQuantity);

		if (promotionQuantity != 0 && generalQuantity == 0) {
			this.promotionStatus = PromotionStatus.getStatus(true, false);
		}
		if (promotionQuantity != 0 && generalQuantity != 0) {
			this.promotionStatus = PromotionStatus.getStatus(true, true);
		}
		if (promotionQuantity == 0 && generalQuantity != 0) {
			this.promotionStatus = PromotionStatus.getStatus(false, true);
		}
	}

	public void updateIsApplied(String answer) {
		this.isApplied = true; // 우선 무조건 적용

		if (answer.equals("N")) {
			this.isApplied = false;
		}
	}

	private void validateFormat(String input) {
		if (!(input.charAt(0) == '[') || !(input.charAt(input.length() - 1) == ']')) {
			throw new IllegalArgumentException(ErrorMessage.INPUT_FORMAT_ERROR_MESSAGE);
		}

		if (!input.substring(1, input.length() - 1).matches("([가-힣]+)-([0-9]+)")) {
			throw new IllegalArgumentException(ErrorMessage.INPUT_FORMAT_ERROR_MESSAGE);
		}
	}

	private void validateInventory(Products products, String input) {
		String[] product = input.substring(1, input.length() - 1).split("-");
		List<Product> inventory = products.getProducts();

		if (!checkProduct(inventory, product[0])) {
			throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_PRODUCT_ERROR_MESSAGE);
		}

		if (Integer.parseInt(product[1]) > checkProductNumber(inventory, product[0])) {
			throw new IllegalArgumentException(ErrorMessage.QUANTITY_OVER_ERROR_MESSAGE);
		}
	}

	private boolean checkProduct(List<Product> inventory, String name) {
		for (Product p : inventory) {
			if (p.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private int checkProductNumber(List<Product> inventory, String name) {
		int sum = 0;
		for (Product p : inventory) {
			if (p.getName().equals(name)) {
				sum += p.getQuantity();
			}
		}
		return sum;
	}

}
