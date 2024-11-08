package store.model;

import java.util.HashMap;
import java.util.List;

public class Receipt {
	private HashMap<Product, Integer> buyingCounter;

	public Receipt(List<BuyingProduct> buyingProducts, Products products) {
		this.buyingCounter = writeProducts(buyingProducts, products);
	}

	public HashMap<Product, Integer> getBuyingCounter() {
		return buyingCounter;
	}

	private HashMap<Product, Integer> writeProducts(List<BuyingProduct> buyingProducts, Products products) {
		HashMap<Product, Integer> counter = new HashMap<>();
		for (BuyingProduct buyingProduct : buyingProducts) {
			int promotionQuantity = buyingProduct.calculatePromotionQuantity(products);
			int generalQuantity = buyingProduct.calculateGeneralQuantity(promotionQuantity);

			if (buyingProduct.getPromotionStatus() == PromotionStatus.APPLIED && !buyingProduct.getIsApplied()) {
				promotionQuantity = buyingProduct.getQuantity();
			}

			Product promotionProduct = products.findPromotionProduct(buyingProduct.getName());
			Product generalProduct = products.findGeneralProduct(buyingProduct.getName());

			counter.put(promotionProduct, promotionQuantity);
			counter.put(generalProduct, generalQuantity);
		}
		return counter;
	}
}
