package store;

import store.controller.FileController;
import store.model.Products;
import store.model.Promotions;

public class Application {
	public static void main(String[] args) {
		Promotions promotions = new Promotions();
		Products products = new Products();

		FileController fileController = new FileController(promotions, products);
		fileController.initializeData("src/main/resources/promotions.md", "src/main/resources/products.md");

	}
}
