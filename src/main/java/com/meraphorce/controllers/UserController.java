package com.meraphorce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meraphorce.controllers.pojos.UserRequest;
import com.meraphorce.controllers.pojos.UserResponse;
import com.meraphorce.exceptions.NoUserInformationException;
import com.meraphorce.exceptions.UserNotFoundException;
import com.meraphorce.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user) {
		return ResponseEntity.ok(userService.createUser(user));
	}

	@PostMapping("/bulk")
	public ResponseEntity<List<UserResponse>> createUsersByBulk(@RequestBody List<UserRequest> users) {
		return ResponseEntity.ok(userService.createUsersByBulk(users));
	}

	@GetMapping("/names")
	public ResponseEntity<List<String>> getUserNames() throws NoUserInformationException {
		return ResponseEntity.ok(userService.getUserNames());
	}

	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers() throws NoUserInformationException {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserByID(@PathVariable String id) throws UserNotFoundException {
		return ResponseEntity.ok(userService.getUserByID(id));
	}

	@PutMapping("/{id}")
	// @PatchMapping("/{id}") Ignored by Spring, Maybe create other method?
	public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user, @PathVariable String id) {
		return ResponseEntity.ok(userService.createUser(user));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
	}

}
