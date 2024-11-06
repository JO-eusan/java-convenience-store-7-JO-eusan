package store.view;

import java.io.FileNotFoundException;

import store.constant.ErrorMessage;
import store.constant.UserMessage;
import store.model.Product;
import store.model.Products;

public class OutputView {
	public void printFileErrorMessage(FileNotFoundException e) {
		System.out.println(ErrorMessage.ERROR_FORMAT + e.getMessage());
	}

	public void printGreeting() {
		System.out.println(UserMessage.GREETING_MESSAGE);
	}

	public void printInventory(Products products) {
		System.out.println(UserMessage.INVENTORY_MESSAGE);
		System.out.println();

		for(Product product : products.getProducts()) {
			System.out.println(product);
		}
		System.out.println();
	}
}
