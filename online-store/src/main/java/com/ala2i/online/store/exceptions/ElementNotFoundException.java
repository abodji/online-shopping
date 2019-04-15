package com.ala2i.online.store.exceptions;

public class ElementNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5294240852064420158L;

	public ElementNotFoundException() {
	}

	public ElementNotFoundException(String message) {
		super(message);
	}
}
