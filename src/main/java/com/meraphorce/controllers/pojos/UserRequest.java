package com.meraphorce.controllers.pojos;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String email;

}
