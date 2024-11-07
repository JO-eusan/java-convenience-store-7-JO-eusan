package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.constant.UserMessage;
import store.model.PromotionStatus;

public class InputView {
	public String readBuyingProduct() {
		System.out.println(UserMessage.BUYING_MESSAGE);
		return Console.readLine();
	}

	public String readApplicable(PromotionStatus promotionStatus) {
		return "";
	}

}
