package store;

import store.constant.FilePath;
import store.controller.BuyingController;
import store.controller.FileController;
import store.model.Products;
import store.model.Promotions;

public class Application {
	public static void main(String[] args) {
		Promotions promotions = new Promotions();
		Products products = new Products();

		FileController fileController = new FileController(promotions, products);
		BuyingController buyingController = new BuyingController(promotions, products);

		fileController.initializeData(FilePath.PROMOTIONS_MD_PATH, FilePath.PRODUCTS_MD_PATH);
		buyingController.startPurchase();
	}
}
