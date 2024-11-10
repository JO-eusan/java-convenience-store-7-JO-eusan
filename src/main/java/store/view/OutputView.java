package store.view;

import java.io.FileNotFoundException;

import store.constant.ErrorMessage;
import store.constant.UserMessage;
import store.model.domain.BuyingProduct;
import store.model.domain.Membership;
import store.model.domain.Product;
import store.model.Products;
import store.model.domain.Receipt;

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

		if (realQuantity > buyingProduct.getQuantity()) {
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

	public void printMembershipQuestion() {
		System.out.println(UserMessage.MEMBERSHIP_QUESTION_MESSAGE);
	}

	public void printReceiptProducts(Receipt receipt, Products products) {
		System.out.println(UserMessage.RECEIPT_START_MESSAGE);
		System.out.println(UserMessage.RECEIPT_COLUMN_MESSAGE);

		for (String productName : receipt.getBuyingNames()) {
			int totalQuantity = receipt.getTotalQuantity(productName);
			int totalPrice = products.findGeneralProduct(productName).getPrice() * totalQuantity;
			System.out.printf(UserMessage.RECEIPT_PRODUCTS_FORMAT, productName, totalQuantity, totalPrice);
		}
	}

	public void printReceiptDum(Receipt receipt) {
		System.out.println(UserMessage.RECEIPT_DUM_MESSAGE);

		for (String productName : receipt.getBuyingNames()) {
			int promotionNum = receipt.getPromotionDumQuantity(productName);
			if (promotionNum != 0) {
				System.out.printf(UserMessage.RECEIPT_DUM_PRODUCTS_FORMAT, productName, promotionNum);
			}
		}
	}

	public void printReceiptPrice(Receipt receipt, Products products, boolean isMembership) {
		int totalQuantity = calculateTotalQuantity(receipt);
		int totalPrice = calculateTotalPrice(receipt, products);
		int promotionDiscount = calculatePromotionDiscount(receipt, products);
		int membershipDiscount = receipt.getMembershipDiscount(Membership.DEFAULT, isMembership);
		int finalPrice = totalPrice - promotionDiscount - membershipDiscount;
		System.out.println(UserMessage.RECEIPT_START_PAYMENT);
		System.out.printf(UserMessage.RECEIPT_TOTAL_PAYMENT, "총구매액", totalQuantity, totalPrice);
		System.out.printf(UserMessage.RECEIPT_DISCOUNT_FORMAT, "행사할인", Math.min(0, -promotionDiscount));
		System.out.printf(UserMessage.RECEIPT_DISCOUNT_FORMAT, "멤버십할인", Math.min(0, -membershipDiscount));
		System.out.printf(UserMessage.RECEIPT_PAYMENT_FORMAT, "내실돈", finalPrice);
	}

	private int calculateTotalQuantity(Receipt receipt) {
		return receipt.getBuyingNames().stream().mapToInt(receipt::getTotalQuantity).sum();
	}

	private int calculateTotalPrice(Receipt receipt, Products products) {
		return receipt.getBuyingNames().stream()
			.mapToInt(name -> products.findGeneralProduct(name).getPrice() * receipt.getTotalQuantity(name))
			.sum();
	}

	private int calculatePromotionDiscount(Receipt receipt, Products products) {
		return receipt.getBuyingNames().stream()
			.mapToInt(name -> receipt.getPromotionDumQuantity(name) * products.findGeneralProduct(name).getPrice())
			.sum();
	}
}
