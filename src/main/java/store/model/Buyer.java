package store.model;

import java.util.ArrayList;
import java.util.List;

import store.constant.ErrorMessage;
import store.model.domain.BuyingProduct;
import store.model.domain.Product;
import store.model.domain.PromotionStatus;

public class Buyer {
	private List<BuyingProduct> buyingProducts;

	public Buyer() {
		this.buyingProducts = new ArrayList<>();
	}

	public List<BuyingProduct> getBuyingProducts() {
		return buyingProducts;
	}

	public void buyProducts(Products products, String input) {
		for (String pair : input.split(",")) {
			if (pair.equals("")) {
				throw new IllegalArgumentException(ErrorMessage.INPUT_FORMAT_ERROR_MESSAGE);
			}
			buyingProducts.add(new BuyingProduct(products, pair));
		}
	}

	public void applyPromotions(Products products) {
		for (BuyingProduct buyingProduct : buyingProducts) {
			int promotionQuantity = buyingProduct.calculatePromotionQuantity(products);
			int generalQuantity = buyingProduct.calculateGeneralQuantity(promotionQuantity);
			buyingProduct.applyPromotion(promotionQuantity, generalQuantity);
		}
	}

	public void pickProducts(Products products) {
		for (BuyingProduct buyingProduct : buyingProducts) {
			int promotionQuantity = adjustPromotionQuantity(buyingProduct, products);
			int generalQuantity = adjustGeneralQuantity(buyingProduct, promotionQuantity, products);
			buyingProduct.setFinalQuantity(promotionQuantity, generalQuantity);

			updateProductInventory(products.findPromotionProduct(buyingProduct.getName()), promotionQuantity);
			updateProductInventory(products.findGeneralProduct(buyingProduct.getName()), generalQuantity);
		}
	}

	private int adjustPromotionQuantity(BuyingProduct buyingProduct, Products products) {
		int promotionQuantity = buyingProduct.calculatePromotionQuantity(products);
		return checkAppliedPromotion(buyingProduct, promotionQuantity);
	}

	private int adjustGeneralQuantity(BuyingProduct buyingProduct, int promotionQuantity, Products products) {
		int generalQuantity = buyingProduct.calculateGeneralQuantity(promotionQuantity);
		return checkPartialAppliedPromotion(buyingProduct, generalQuantity);
	}

	private void updateProductInventory(Product product, int quantity) {
		if (product != null) {
			product.subQuantity(quantity);
		}
	}

	private int checkAppliedPromotion(BuyingProduct buyingProduct, int promotionQuantity) {
		PromotionStatus promotionStatus = buyingProduct.getPromotionStatus();
		boolean isApplied = buyingProduct.getIsApplied();

		if(promotionStatus == PromotionStatus.APPLIED && !isApplied) {
			promotionQuantity = buyingProduct.getQuantity();
		}

		return promotionQuantity;
	}

	private int checkPartialAppliedPromotion(BuyingProduct buyingProduct, int generalQuantity) {
		PromotionStatus promotionStatus = buyingProduct.getPromotionStatus();
		boolean isApplied = buyingProduct.getIsApplied();

		if(promotionStatus == PromotionStatus.PARTIALLY_APPLIED && !isApplied) {
			generalQuantity = 0;
		}

		return generalQuantity;
	}

}
