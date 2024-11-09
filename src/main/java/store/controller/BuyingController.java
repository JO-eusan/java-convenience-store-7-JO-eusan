package store.controller;

import store.model.Buyer;
import store.model.BuyingProduct;
import store.model.Products;
import store.model.PromotionStatus;
import store.model.Promotions;
import store.model.Receipt;
import store.view.InputView;
import store.view.OutputView;

public class BuyingController {
	InputView inputView;
	OutputView outputView;
	Buyer buyer;
	Promotions promotions;
	Products products;

	public BuyingController(Promotions promotions, Products products) {
		this.inputView = new InputView();
		this.outputView = new OutputView();
		this.buyer = new Buyer();

		this.promotions = promotions;
		this.products = products;
	}

	public void startPurchase() {
		do {
			showGreeting();
			showInventory();
			executePurchase();
			askPromotion();
			organizeInventory();
			showReceipt();
		} while (showRetryQuestion().equals("Y"));
	}

	private String showRetryQuestion() {
		outputView.printRetryQuestion();
		try {
			String input = inputView.readApplicable();
			resetBuyer(input);
			return input;
		} catch (IllegalArgumentException e) {
			outputView.printArgumentErrorMessage(e);
			return showRetryQuestion();
		}
	}

	private void resetBuyer(String input) {
		if(input.equals("Y")) {
			this.buyer = new Buyer();
		}
	}

	private void showGreeting() {
		outputView.printGreeting();
	}

	private void showInventory() {
		outputView.printInventory(products);
	}

	private void executePurchase() {
		try {
			buyer.buyProducts(products, inputView.readBuyingProduct());
		} catch (IllegalArgumentException e) {
			outputView.printArgumentErrorMessage(e);
			executePurchase();
		}

		buyer.applyPromotions(products);
	}

	private void askPromotion() {
		for (BuyingProduct buyingProduct : buyer.getBuyingProducts()) {
			if (buyingProduct.getPromotionStatus() == PromotionStatus.APPLIED
				&& outputView.printAppliedQuestion(products, buyingProduct)) {
				buyingProduct.updateIsApplied(showQuestion());
			}
			if (buyingProduct.getPromotionStatus() == PromotionStatus.PARTIALLY_APPLIED) {
				outputView.printPurchaseQuestion(products, buyingProduct);
				buyingProduct.updateIsApplied(showQuestion());
			}
		}
	}

	private String showQuestion() {
		try {
			return inputView.readApplicable();
		} catch (IllegalArgumentException e) {
			outputView.printArgumentErrorMessage(e);
			return showQuestion();
		}
	}

	private void organizeInventory() {
		buyer.pickProducts(products);
	}

	private void showReceipt() {
		outputView.printMembershipQuestion();
		boolean isMembership = showQuestion().equals("Y");

		Receipt receipt = new Receipt(buyer.getBuyingProducts(), products);
		outputView.printReceiptProducts(receipt, products);
		outputView.printReceiptDum(receipt);
		outputView.printReceiptPrice(receipt, products, isMembership);
	}
}
