package store.model;

import java.util.ArrayList;
import java.util.List;

public class Products {
	private List<Product> products;

	public Products() {
		this.products = new ArrayList<>();
	}

	public void addProduct(Product product) {
		products.add(product);
	}

	public void addGeneralProduct() {
		for (int i = 0; i < products.size(); i++) {
			Product p = products.get(i);

			if (!hasGeneralProduct(p.getName())) {
				products.add(i, new Product(p.getName(), p.getPrice(), p.getQuantity(), null));
			}
		}
	}

	private boolean hasGeneralProduct(String productName) {
		return products.stream()
			.filter(product -> product.getName().equals(productName))
			.anyMatch(product -> product.getPromotion() == null);
	}
}
