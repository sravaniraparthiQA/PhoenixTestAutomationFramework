package com.api.constant;

public enum Product {

	NEXUS_2(1), PIXEL(2);

	int code;

	private Product(int code) {		//only private constructors allowed in enum!!
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
