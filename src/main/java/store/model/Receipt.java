package store.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receipt {
	private List<String> buyingNames;
	private HashMap<Product, Integer> buyingCounter;

	public Receipt(List<BuyingProduct> buyingProducts, Products products) {
		this.buyingNames = writeCategory(buyingProducts);
		this.buyingCounter = writeProducts(buyingProducts, products);
	}

	public List<String> getBuyingNames() {
		return buyingNames;
	}

	public int getPromotionDumQuantity(String productName) {
		int dumQuantity = 0;

		for (Map.Entry<Product, Integer> entry : buyingCounter.entrySet()) {
			Product product = entry.getKey();
			int num = entry.getValue();

			if (product.getName().equals(productName) && product.getPromotion() != null) {
				dumQuantity = num/product.getPromotion().getUnit() * product.getPromotion().getGetNumber();
			}
		}

		return dumQuantity;
	}

	public int getTotalQuantity(String productName) {
		int totalQuantity = 0;

		for (Map.Entry<Product, Integer> entry : buyingCounter.entrySet()) {
			Product product = entry.getKey();
			int quantity = entry.getValue();

			if (product.getName().equals(productName)) {
				totalQuantity += quantity;
			}
		}

		return totalQuantity;
	}

	public int getMembershipDiscount(Membership membership, boolean isMembership) {
		int totalGeneralPrice = 0;
		for (Map.Entry<Product, Integer> entry : buyingCounter.entrySet()) {
			Product product = entry.getKey();
			int num = entry.getValue();

			if (product.getPromotion() == null) {
				totalGeneralPrice += num * product.getPrice();
			}
		}
		return membership.calculateDiscountAmount(totalGeneralPrice, isMembership);
	}

	private List<String> writeCategory(List<BuyingProduct> buyingProducts) {
		List<String> buyingNames = new ArrayList<>();
		for(BuyingProduct buyingProduct : buyingProducts) {
			buyingNames.add(buyingProduct.getName());
		}
		return buyingNames;
	}

	private HashMap<Product, Integer> writeProducts(List<BuyingProduct> buyingProducts, Products products) {
		HashMap<Product, Integer> counter = new HashMap<>();
		for (BuyingProduct buyingProduct : buyingProducts) {
			int promotionQuantity = buyingProduct.getPromotionQuantity();
			int generalQuantity = buyingProduct.getGeneralQuantity();

			Product promotionProduct = products.findPromotionProduct(buyingProduct.getName());
			Product generalProduct = products.findGeneralProduct(buyingProduct.getName());

			if(promotionProduct != null) {
				counter.put(promotionProduct, promotionQuantity);
			}
			counter.put(generalProduct, generalQuantity);
		}
		return counter;
	}
}
