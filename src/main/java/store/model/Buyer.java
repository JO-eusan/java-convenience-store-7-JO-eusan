package store.model;

import java.util.ArrayList;
import java.util.List;

import store.constant.ErrorMessage;

public class Buyer {
	private List<BuyingProduct> buyingProducts;

	public Buyer() {
		this.buyingProducts = new ArrayList<>();
	}

	public List<BuyingProduct> getBuyingProducts() {
		return buyingProducts;
	}

	public void buyProducts(Products products, String input) {
		String[] productsPair = input.split(",");
		for (String pair : productsPair) {
			if (pair.equals("")) {
				throw new IllegalArgumentException(ErrorMessage.INPUT_FORMAT_ERROR_MESSAGE);
			}
			buyingProducts.add(new BuyingProduct(products, pair));
		}
	}

	public void applyPromotions(Products products) {
		for (BuyingProduct buyingProduct : buyingProducts) {
			buyingProduct.applyPromotion(products);
		}
	}

}
