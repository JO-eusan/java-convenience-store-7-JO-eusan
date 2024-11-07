package store.model;

import java.util.HashMap;
import java.util.Map;

public class Promotions {
	private Map<String, Promotion> promotions;

	public Promotions() {
		this.promotions = new HashMap<>();
	}

	public Map<String, Promotion> getPromotions() {
		return promotions;
	}

	public void addPromotion(String name, Promotion promotion) {
		promotions.put(name, promotion);
	}
}
