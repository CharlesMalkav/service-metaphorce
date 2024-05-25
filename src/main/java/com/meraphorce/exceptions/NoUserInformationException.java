package com.meraphorce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "No information")
public class NoUserInformationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoUserInformationException(String message) {
		super(message);
	}

	public NoUserInformationException(String message, Throwable cause) {
		super(message, cause);
	}

}
