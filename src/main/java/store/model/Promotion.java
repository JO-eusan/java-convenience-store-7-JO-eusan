package store.model;

import java.time.LocalDate;

public class Promotion {
	private String name;
	private int buyNumber;
	private int getNumber;
	private LocalDate start_date;
	private LocalDate end_date;

	public Promotion(String name, int buyNumber, int getNumber, LocalDate start_date, LocalDate end_date) {
		this.name = name;
		this.buyNumber = buyNumber;
		this.getNumber = getNumber;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public String getName() {
		return name;
	}

	public int getBuyNumber() {
		return buyNumber;
	}

	public int getGetNumber() {
		return getNumber;
	}

	public int getUnit() {
		return buyNumber + getNumber;
	}

	public boolean checkUsable(LocalDate now) {
		if ((now.isEqual(start_date) || now.isAfter(start_date)) && (now.isEqual(end_date) || now.isBefore(end_date))) {
			return true;
		}
		return false;
	}

}
