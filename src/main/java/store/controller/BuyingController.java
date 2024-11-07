package store.controller;

import store.model.BuyingProducts;
import store.model.Products;
import store.model.Promotions;
import store.view.InputView;
import store.view.OutputView;

public class BuyingController {
	InputView inputView;
	OutputView outputView;
	BuyingProducts buyingProducts;
	Promotions promotions;
	Products products;

	public BuyingController(Promotions promotions, Products products) {
		this.inputView = new InputView();
		this.outputView = new OutputView();
		this.buyingProducts = new BuyingProducts();

		this.promotions = promotions;
		this.products = products;
	}

	public void startPurchase() {
		showGreeting();
		showInventory();
		purchase();
	}

	private void showGreeting() {
		outputView.printGreeting();
	}

	private void showInventory() {
		outputView.printInventory(products);
	}

	private void purchase() {
		try {
			buyingProducts.buyProduct(products, inputView.readBuyingProduct());
		} catch(IllegalArgumentException e) {
			outputView.printArgumentErrorMessage(e);
			purchase();
		}

	}
}
