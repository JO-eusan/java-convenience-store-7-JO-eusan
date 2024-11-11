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
			int promotionQuantity = buyingProduct.calculatePromotionQuantity(products);
			int generalQuantity = buyingProduct.calculateGeneralQuantity(promotionQuantity);
			promotionQuantity = checkAppliedPromotion(buyingProduct, promotionQuantity);
			generalQuantity = checkPartialAppliedPromotion(buyingProduct, generalQuantity);

			buyingProduct.setFinalQuantity(promotionQuantity, generalQuantity);
			int newGeneralQuantity = adjustPromotionProduct(buyingProduct, products, promotionQuantity, generalQuantity);
			adjustGeneralProduct(buyingProduct, products, newGeneralQuantity);
		}
	}

	private int adjustPromotionProduct(BuyingProduct buyingProduct, Products products, int promotionQuantity, int generalQuantity) {
		Product promotionProduct = products.findPromotionProduct(buyingProduct.getName());

		if (promotionProduct != null) {
			promotionProduct.subQuantity(promotionQuantity);
		}
		if (promotionProduct != null && generalQuantity >= promotionProduct.getQuantity()) {
			generalQuantity -= promotionProduct.getQuantity();
			promotionProduct.subQuantity(promotionProduct.getQuantity());
		}
		return generalQuantity;
	}

	private void adjustGeneralProduct(BuyingProduct buyingProduct, Products products, int generalQuantity) {
		Product generalProduct = products.findGeneralProduct(buyingProduct.getName());
		generalProduct.subQuantity(generalQuantity);
	}

	private int checkAppliedPromotion(BuyingProduct buyingProduct, int promotionQuantity) {
		PromotionStatus promotionStatus = buyingProduct.getPromotionStatus();
		boolean isApplied = buyingProduct.getIsApplied();

		if (promotionStatus == PromotionStatus.APPLIED && !isApplied) {
			promotionQuantity = buyingProduct.getQuantity();
		}

		return promotionQuantity;
	}

	private int checkPartialAppliedPromotion(BuyingProduct buyingProduct, int generalQuantity) {
		PromotionStatus promotionStatus = buyingProduct.getPromotionStatus();
		boolean isApplied = buyingProduct.getIsApplied();

		if (promotionStatus == PromotionStatus.PARTIALLY_APPLIED && !isApplied) {
			generalQuantity = 0;
		}

		return generalQuantity;
	}

}
