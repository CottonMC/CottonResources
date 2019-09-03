package io.github.cottonmc.resources.util;

public class NotEnoughJsonException extends Exception {
	private static final long serialVersionUID = -8184553192318419242L;
	
	public NotEnoughJsonException(String message) {
		super(message);
	}
	
	public NotEnoughJsonException(String message, Throwable t) {
		super(message, t);
	}
	
}
