package com.db.awmd.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserAccountNotFoundException extends RuntimeException {

	public UserAccountNotFoundException(String message) {
		super(message);
	}

}
