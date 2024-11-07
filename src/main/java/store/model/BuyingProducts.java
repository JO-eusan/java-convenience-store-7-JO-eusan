package store.model;

import java.util.ArrayList;
import java.util.List;

import store.constant.ErrorMessage;

public class BuyingProducts {
	private List<BuyingProduct> buyingProducts;

	public BuyingProducts() {
		this.buyingProducts = new ArrayList<>();
	}

	public void buyProducts(Products products, String input) {
		String[] productsPair = input.split(",");
		for(String pair : productsPair) {
			if(pair.equals("")) {
				throw new IllegalArgumentException(ErrorMessage.INPUT_FORMAT_ERROR_MESSAGE);
			}
			buyingProducts.add(new BuyingProduct(products, pair));
		}
	}

	public void applyPromotions(Products products) {
		for(BuyingProduct buyingProduct : buyingProducts) {
			Product promotionProduct = products.getPromotionProduct(buyingProduct.getName());
			Product generalProduct = products.getGeneralProduct(buyingProduct.getName());

			if(promotionProduct.getPromotion().checkUsable()) {
				buyingProduct.applyPromotion(promotionProduct, generalProduct);
			}
		}
	}


}
