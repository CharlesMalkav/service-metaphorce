package com.meraphorce.controllers.pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meraphorce.models.User;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String email;
	private Date createdDate;
	private List<String> errors;

	public UserResponse(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
	}

	public UserResponse(UserRequest userRequest, List<String> errors) {
		this.id = userRequest.getId();
		this.name = userRequest.getName();
		this.email = userRequest.getEmail();
		this.errors = errors;
	}

}
