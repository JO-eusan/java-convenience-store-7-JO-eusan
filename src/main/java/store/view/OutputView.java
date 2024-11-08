package store.view;

import java.io.FileNotFoundException;

import store.constant.ErrorMessage;
import store.constant.UserMessage;
import store.model.BuyingProduct;
import store.model.Product;
import store.model.Products;

public class OutputView {
	public void printFileErrorMessage(FileNotFoundException e) {
		System.out.println(ErrorMessage.ERROR_FORMAT + e.getMessage());
	}

	public void printArgumentErrorMessage(IllegalArgumentException e) {
		System.out.println(ErrorMessage.ERROR_FORMAT + e.getMessage());
	}

	public void printGreeting() {
		System.out.println(UserMessage.GREETING_MESSAGE);
	}

	public void printInventory(Products products) {
		System.out.println(UserMessage.INVENTORY_MESSAGE);
		System.out.println();

		for (Product product : products.getProducts()) {
			System.out.println(product);
		}
		System.out.println();
	}

	public boolean printAppliedQuestion(Products products, BuyingProduct buyingProduct) {
		int promotionQuantity = buyingProduct.calculatePromotionQuantity(products);
		int generalQuantity = buyingProduct.calculateGeneralQuantity(promotionQuantity);
		int realQuantity = promotionQuantity + generalQuantity;

		if(realQuantity > buyingProduct.getQuantity()) {
			System.out.printf(UserMessage.APPLIED_QUESTION_MESSAGE, buyingProduct.getName(),
				realQuantity - buyingProduct.getQuantity());
			return true;
		}
		return false;
	}

	public void printPurchaseQuestion(Products products, BuyingProduct buyingProduct) {
		int promotionQuantity = buyingProduct.calculatePromotionQuantity(products);
		int generalQuantity = buyingProduct.calculateGeneralQuantity(promotionQuantity);

		System.out.printf(UserMessage.PARTIALLY_APPLIED_QUESTION_MESSAGE, buyingProduct.getName(), generalQuantity);
	}
}
