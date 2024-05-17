package com.meraphorce.controllers.pojos;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.meraphorce.models.User;

import lombok.Data;

@Data
public class UserResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String email;
	private Date currentRequest;

	public UserResponse(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.currentRequest = Calendar.getInstance().getTime();
	}

}
