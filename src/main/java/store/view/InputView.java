package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.constant.UserMessage;

public class InputView {
	public String readBuyingProduct() {
		System.out.println(UserMessage.BUYING_MESSAGE);
		return Console.readLine();
	}

}
