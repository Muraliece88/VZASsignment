package com.vz.app.order;

public class OrderExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public OrderExistsException(String message) {
		super(message);
	}
}
