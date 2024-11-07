package store.model;

import java.util.List;

import store.constant.ErrorMessage;

public class BuyingProduct {
	private String name;
	private int quantity;
	private PromotionStatus promotionStatus;

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

	public PromotionStatus getPromotionStatus() {
		return promotionStatus;
	}

	public void applyPromotion(Product promotion, Product general) {
		if(general.getQuantity() >= quantity) {
			this.promotionStatus = PromotionStatus.getStatus(false, true);
		}
		if(promotion != null && promotion.getQuantity() + general.getQuantity() >= quantity) {
			this.promotionStatus = PromotionStatus.getStatus(true, true);
		}
		if(promotion != null && promotion.getQuantity() >= quantity) {
			this.promotionStatus = PromotionStatus.getStatus(true, false);
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
