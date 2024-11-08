package store.controller;

import store.model.Buyer;
import store.model.Products;
import store.model.Receipt;
import store.view.InputView;
import store.view.OutputView;

public class PaymentController {
	InputView inputView;
	OutputView outputView;
	Products products;
	Buyer buyer;
	Receipt receipt;

	public PaymentController(Products products, Buyer buyer) {
		this.inputView = new InputView();
		this.outputView = new OutputView();

		this.products = products;
		this.buyer = buyer;
		this.receipt = new Receipt(buyer.getBuyingProducts(), products);
	}

	public void checkout() {

	}
}
