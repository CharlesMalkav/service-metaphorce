package com.meraphorce.controllers.pojos;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotNull(message = "The id is mandatory")
	@NotEmpty(message = "The id must not be blank")
	private String id;
	@NotNull(message = "The name is mandatory")
	@NotEmpty(message = "The name must not be blank")
	private String name;
	@NotNull(message = "The email is mandatory")
	@NotEmpty(message = "The email must not be blank")
	private String email;

}
