package com.ala2i.online.store.exceptions;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = -5309120672996566270L;

	public FileStorageException() {
	
	}

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
