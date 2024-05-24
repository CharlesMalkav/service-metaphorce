package com.meraphorce.models;

import java.io.Serializable;

import com.meraphorce.controllers.pojos.UserRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String name;
	private String email;

	public User(UserRequest userRequest) {
		this.id = userRequest.getId();
		this.name = userRequest.getName();
		this.email = userRequest.getEmail();
	}

}
