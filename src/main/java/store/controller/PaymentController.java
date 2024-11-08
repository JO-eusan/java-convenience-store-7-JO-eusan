package store.controller;

import store.model.Buyer;
import store.model.Products;
import store.model.Receipt;
import store.view.InputView;
import store.view.OutputView;

public class PaymentController {
	InputView inputView;
	OutputView outputView;
	Buyer buyer;
	Products products;
	Receipt receipt;

	public PaymentController(Buyer buyer, Products products) {
		this.inputView = new InputView();
		this.outputView = new OutputView();

		this.buyer = buyer;
		this.products = products;
		this.receipt = new Receipt(buyer.getBuyingProducts(), products);
	}

	public void checkout() {
		showReceipt();
	}

	private void showReceipt() {
		outputView.printReceiptProducts(receipt, products);
		outputView.printReceiptDum(receipt);
		outputView.printReceiptPrice(receipt);
	}
}
