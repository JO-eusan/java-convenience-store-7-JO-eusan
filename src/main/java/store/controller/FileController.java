package store.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

import store.model.domain.Product;
import store.model.domain.Promotion;
import store.view.OutputView;
import store.model.Products;
import store.model.Promotions;

public class FileController {
	OutputView outputView;
	Promotions promotions;
	Products products;

	public FileController(Promotions promotions, Products products) {
		this.outputView = new OutputView();
		this.promotions = promotions;
		this.products = products;
	}

	public void initializeData(String promotionsPath, String productsPath) {
		try {
			createPromotions(loadFile(promotionsPath));
			createProducts(loadFile(productsPath));
		} catch (FileNotFoundException e) {
			outputView.printFileErrorMessage(e);
		}
	}

	private Scanner loadFile(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);

		if (scanner.hasNextLine()) {
			scanner.nextLine();
		}

		return scanner;
	}

	private void createPromotions(Scanner promotionsScanner) {
		while (promotionsScanner.hasNextLine()) {
			String[] attr = promotionsScanner.nextLine().split(",");
			String name = attr[0];
			int buyNumber = Integer.parseInt(attr[1]);
			int getNumber = Integer.parseInt(attr[2]);
			LocalDate start_date = LocalDate.parse(attr[3]);
			LocalDate end_date = LocalDate.parse(attr[4]);

			promotions.addPromotion(name, new Promotion(name, buyNumber, getNumber, start_date, end_date));
		}
	}

	private void createProducts(Scanner productsScanner) {
		while (productsScanner.hasNextLine()) {
			String[] attr = productsScanner.nextLine().split(",");
			String name = attr[0];
			int price = Integer.parseInt(attr[1]);
			int quantity = Integer.parseInt(attr[2]);
			Promotion promotion = promotions.getPromotions().get(attr[3]);

			products.addProduct(new Product(name, price, quantity, promotion));
		}
		products.addGeneralProduct();
	}
}
