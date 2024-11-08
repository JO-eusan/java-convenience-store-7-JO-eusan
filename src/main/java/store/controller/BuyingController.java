package store.controller;

import store.model.Buyer;
import store.model.BuyingProduct;
import store.model.Products;
import store.model.Promotions;
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
		showGreeting();
		showInventory();
		purchase();
		askPromotion();
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
		for(BuyingProduct buyingProduct : buyer.getBuyingProducts()) {
			if(outputView.printQuestion(products, buyingProduct)) {
				String answer = showPromotionQuestion(buyingProduct);

			}



		}
	}

	private String showPromotionQuestion(BuyingProduct buyingProduct) {
		try {
			String input = inputView.readApplicable();
			return input;
		} catch(IllegalArgumentException e) {
			outputView.printArgumentErrorMessage(e);
			showPromotionQuestion(buyingProduct);
		}
		return "";
	}

}
