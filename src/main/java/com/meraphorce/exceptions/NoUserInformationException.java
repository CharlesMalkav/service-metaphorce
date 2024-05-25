package com.meraphorce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "No information")
public class NoUserInformationException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;

}
