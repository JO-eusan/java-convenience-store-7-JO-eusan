package store.model.domain;

public enum PromotionStatus {
	APPLIED(true, false),
	NOT_APPLIED(false, true),
	PARTIALLY_APPLIED(true, true),
	NO_INVENTORY(false, false);

	private final boolean isApplied; // 프로모션 적용 상품 존재
	private final boolean isNotApplied; // 프로모션 미적용 상품 존재

	PromotionStatus(boolean isApplied, boolean isNotApplied) {
		this.isApplied = isApplied;
		this.isNotApplied = isNotApplied;
	}

	public static PromotionStatus getStatus(boolean isApplied, boolean isNotApplied) {
		if (isApplied && !isNotApplied) {
			return APPLIED;
		}
		if (!isApplied && isNotApplied) {
			return NOT_APPLIED;
		}
		if (isApplied && isNotApplied) {
			return PARTIALLY_APPLIED;
		}
		return NO_INVENTORY;
	}
}
