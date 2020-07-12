package com.db.awmd.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "sender and receiver are same")
public class SameSenderReceiverException extends RuntimeException {

	public SameSenderReceiverException(String message) {
		super(message);
	}
}
