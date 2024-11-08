package store.constant;

public class UserMessage {
	public static final String GREETING_MESSAGE = "안녕하세요. W편의점입니다.";
	public static final String INVENTORY_MESSAGE = "현재 보유하고 있는 상품입니다.";
	public static final String INVENTORY_FORMAT = "- %s %,d원 %s %s";
	public static final String INVENTORY_OUT_MESSAGE = "재고 없음";
	public static final String INVENTORY_IN_MESSAGE = "%d개";

	public static final String BUYING_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
	public static final String APPLIED_QUESTION_MESSAGE = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
	public static final String PARTIALLY_APPLIED_QUESTION_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
	public static final String RETRY_MESSAGE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

	public static final String RECEIPT_START_MESSAGE = "==============W 편의점================";
	public static final String RECEIPT_COLUMN_MESSAGE = String.format("%-10s %5s %10s", "상품명", "수량", "금액");
	public static final String RECEIPT_PRODUCTS_FORMAT = "%-10s %5d %,10d\n";
	public static final String RECEIPT_DUM_MESSAGE = "=============증\t정===============";
	public static final String RECEIPT_DUM_PRODUCTS_FORMAT = "%-10s %5d\n";
	public static final String RECEIPT_START_PAYMENT = "====================================";
}
