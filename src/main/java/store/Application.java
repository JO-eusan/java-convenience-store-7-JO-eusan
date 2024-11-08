package store;

import store.constant.FilePath;
import store.controller.BuyingController;
import store.controller.FileController;
import store.controller.PaymentController;
import store.model.Buyer;
import store.model.Products;
import store.model.Promotions;

public class Application {
	public static void main(String[] args) {
		Promotions promotions = new Promotions();
		Products products = new Products();
		Buyer buyer = new Buyer();

		FileController fileController = new FileController(promotions, products);
		fileController.initializeData(FilePath.PROMOTIONS_MD_PATH, FilePath.PRODUCTS_MD_PATH);

		BuyingController buyingController = new BuyingController(promotions, products, buyer);
		buyingController.startPurchase();

		PaymentController paymentController = new PaymentController(products, buyer);
		paymentController.checkout();
	}
}
