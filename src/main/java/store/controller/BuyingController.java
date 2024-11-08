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

	public BuyingController(Promotions promotions, Products products, Buyer buyer) {
		this.inputView = new InputView();
		this.outputView = new OutputView();

		this.buyer = buyer;
		this.promotions = promotions;
		this.products = products;
	}

	public void startPurchase() {
		showGreeting();
		showInventory();
		purchase();
		askPromotion();
		organizeInventory();
	}

	private void showGreeting() {
		outputView.printGreeting();
	}

	private void showInventory() {
		outputView.printInventory(products);
	}

	private void purchase() {
		try {
			buyer.buyProducts(products, inputView.readBuyingProduct());
		} catch (IllegalArgumentException e) {
			outputView.printArgumentErrorMessage(e);
			purchase();
		}

		buyer.applyPromotions(products);
	}

	private void askPromotion() {
		for (BuyingProduct buyingProduct : buyer.getBuyingProducts()) {
			String answer = "";
			if (buyingProduct.getPromotionStatus() == PromotionStatus.APPLIED && outputView.printAppliedQuestion(
				products, buyingProduct)) {
				answer = showPromotionQuestion(buyingProduct);
			}
			if (buyingProduct.getPromotionStatus() == PromotionStatus.PARTIALLY_APPLIED) {
				outputView.printPurchaseQuestion(products, buyingProduct);
				answer = showPromotionQuestion(buyingProduct);
			}
			buyingProduct.updateIsApplied(answer);
		}
	}

	private String showPromotionQuestion(BuyingProduct buyingProduct) {
		try {
			String input = inputView.readApplicable();
			return input;
		} catch (IllegalArgumentException e) {
			outputView.printArgumentErrorMessage(e);
			showPromotionQuestion(buyingProduct);
		}
		return "";
	}

	private void organizeInventory() {
		buyer.pickProducts(products);
	}
}
