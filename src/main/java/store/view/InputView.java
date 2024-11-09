package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.constant.ErrorMessage;
import store.constant.UserMessage;

public class InputView {
	public String readBuyingProduct() {
		System.out.println(UserMessage.BUYING_MESSAGE);
		return Console.readLine();
	}

	public String readApplicable() {
		String input = Console.readLine();

		if (input.charAt(0) != 'Y' && input.charAt(0) != 'N') {
			throw new IllegalArgumentException(ErrorMessage.NOT_ANSWER_FORMAT_ERROR_MESSAGE);
		}

		return input;
	}

}
