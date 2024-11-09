package store.model.domain;

public enum Membership {
	DEFAULT(0.3, 8000);

	private final double discount;
	private final int maxPrice;

	Membership(double discount, int maxPrice) {
		this.discount = discount;
		this.maxPrice = maxPrice;
	}

	public int calculateDiscountAmount(int productPrice, boolean isMembership) {
		if(isMembership) {
			int discountAmount = (int) (productPrice * discount);
			return Math.min(discountAmount, maxPrice);
		}
		return 0;
	}

}
