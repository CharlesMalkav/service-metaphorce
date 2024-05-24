package com.meraphorce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meraphorce.controllers.pojos.UserRequest;
import com.meraphorce.controllers.pojos.UserResponse;
import com.meraphorce.exceptions.UserNotFoundException;
import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @param userRequest - POJO that contains the user info
	 * @return
	 */
	public UserResponse createUser(UserRequest userRequest) {

		// Serialize the request into a User entity
		User userEntity = new User(userRequest);
		// Do persistence 
		userRepository.save(userEntity);

		// Serialize the User entity into a UserResponse and return it
		return new UserResponse(userEntity);
	}

	public List<UserResponse> getAllUsers() {

		// Fetching the list of the User entities
		List<User> userEntityList = userRepository.findAll();

		// Parse the User entities list into a list of UserResponse and return it 
		return userEntityList.stream().map(ur -> new UserResponse(ur)).collect(Collectors.toList());
	}

	public UserResponse getUserByID(String userId) throws UserNotFoundException {

		// Fetching the User entity 
		User user = userRepository.findById(userId).orElse(null);

		// If the User entity is null then throw an exception, else return the User entity
		if (user == null)
			throw new UserNotFoundException("User not found");

		return new UserResponse(user);
	}

	public void deleteUser(String userId) {
		// Delete the User entity from the database, if the entity doesn´t exists then the repository do nothing
		userRepository.deleteById(userId);
	}

}
