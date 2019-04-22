package com.ala2i.online.store.exceptions;

public class ElementExistsException extends RuntimeException {

	private static final long serialVersionUID = 3861274650448821868L;

	public ElementExistsException() {
	}

	public ElementExistsException(String message) {
		super(message);
	}
}
