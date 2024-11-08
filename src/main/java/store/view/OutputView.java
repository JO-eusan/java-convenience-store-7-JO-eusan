package store.view;

import java.io.FileNotFoundException;

import store.constant.ErrorMessage;
import store.constant.UserMessage;
import store.model.BuyingProduct;
import store.model.Product;
import store.model.Products;
import store.model.Receipt;

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

	public void printRetryQuestion() {
		System.out.println(UserMessage.RETRY_MESSAGE);
	}

	public void printReceiptProducts(Receipt receipt, Products products) {
		System.out.println(UserMessage.RECEIPT_START_MESSAGE);
		System.out.println(UserMessage.RECEIPT_COLUMN_MESSAGE);

		for(String productName : receipt.getBuyingNames()) {
			int totalQuantity = receipt.getTotalQuantity(productName);
			int totalPrice = products.findGeneralProduct(productName).getPrice() * totalQuantity;
			System.out.printf(UserMessage.RECEIPT_PRODUCTS_FORMAT, productName, totalQuantity, totalPrice);
		}
	}

	public void printReceiptDum(Receipt receipt) {
		System.out.println(UserMessage.RECEIPT_DUM_MESSAGE);

		for(String productName : receipt.getBuyingNames()) {
			int promotionNum = receipt.getPromotionDumQuantity(productName);
			if(promotionNum != 0) {
				System.out.printf(UserMessage.RECEIPT_DUM_PRODUCTS_FORMAT, productName, promotionNum);
			}
		}
	}

	public void printReceiptPrice(Receipt receipt) {
		System.out.println(UserMessage.RECEIPT_START_PAYMENT);
	}
}
