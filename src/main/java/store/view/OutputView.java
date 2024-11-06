package store.view;

import java.io.FileNotFoundException;

import store.constant.ErrorMessage;

public class OutputView {
	public void printFileErrorMessage(FileNotFoundException e) {
		System.out.println(ErrorMessage.ERROR_FORMAT + e.getMessage());
	}
}
