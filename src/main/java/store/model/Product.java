package store.model;

import store.constant.UserMessage;

public class Product {
	private String name;
	private int price;
	private int quantity;
	private Promotion promotion;

	public Product(String name, int price, int quantity, Promotion promotion) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.promotion = promotion;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void subQuantity(int buyingNumber) {
		if(quantity < buyingNumber) {
			throw new IllegalArgumentException();
		}
		this.quantity -= buyingNumber;
	}

	@Override
	public String toString() {
		String quantityStr = UserMessage.INVENTORY_OUT_MESSAGE;
		String promotionStr = "";

		if(quantity > 0) {
			quantityStr = String.format(UserMessage.INVENTORY_IN_MESSAGE, quantity);
		}
		if(promotion != null) {
			promotionStr = promotion.getName();
		}
		return String.format(UserMessage.INVENTORY_FORMAT, name, price, quantityStr, promotionStr);
	}
}
