package store.view;

import java.io.FileNotFoundException;

import store.constant.ErrorMessage;
import store.constant.UserMessage;
import store.model.BuyingProduct;
import store.model.Product;
import store.model.Products;
import store.model.PromotionStatus;

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

	public boolean printQuestion(Products products, BuyingProduct buyingProduct) {
		int promotionQuantity = buyingProduct.calculatePromotionQuantity(products);
		int generalQuantity = buyingProduct.calculateGeneralQuantity(promotionQuantity);
		int realQuantity = promotionQuantity + generalQuantity;

		if (buyingProduct.getPromotionStatus() == PromotionStatus.APPLIED
			&& realQuantity > buyingProduct.getQuantity()) {
			System.out.printf(UserMessage.APPLIED_QUESTION_MESSAGE, buyingProduct.getName(),
				realQuantity - buyingProduct.getQuantity());
			return true;
		}
		if (buyingProduct.getPromotionStatus() == PromotionStatus.PARTIALLY_APPLIED) {
			System.out.printf(UserMessage.PARTIALLY_APPLIED_QUESTION_MESSAGE, buyingProduct.getName(), generalQuantity);
			return true;
		}
		return false;
	}
}
