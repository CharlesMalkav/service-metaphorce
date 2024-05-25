package com.meraphorce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meraphorce.controllers.pojos.UserRequest;
import com.meraphorce.controllers.pojos.UserResponse;
import com.meraphorce.exceptions.NoUserInformationException;
import com.meraphorce.exceptions.UserNotFoundException;
import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @param userRequest - POJO that contains the user info
	 * @return
	 */
	public UserResponse createUser(UserRequest userRequest) {

		log.info("Starting to save user");
		// Serialize the request into a User entity
		User userEntity = new User(userRequest);
		log.info("Saving user");
		// Do persistence 
		userRepository.save(userEntity);
		log.info("User saved. returning User...");

		// Serialize the User entity into a UserResponse and return it
		return new UserResponse(userEntity);
	}

	/**
	 * 
	 * @param users - a list of users to save
	 * @return the saved users
	 */
	public List<UserResponse> createUsersByBulk(List<UserRequest> users) {

		log.info("Starting to save user list, serializing into User entity");
		List<User> userEntityList = users.stream().map(us -> new User(us)).collect(Collectors.toList());
		log.debug(String.format("Entities gotten: \n %s", userEntityList.toString()));
		log.info("Saving users...");
		userRepository.saveAll(userEntityList);
		log.info("Users saved, returning...");

		return userEntityList.stream().map(ur -> new UserResponse(ur)).collect(Collectors.toList());
	}

	public List<UserResponse> getAllUsers() throws NoUserInformationException {

		// Fetching the list of the User entities
		List<User> userEntityList = userRepository.findAll();

		if (userEntityList.isEmpty())
			throw new NoUserInformationException("No Users information");

		// Parse the User entities list into a list of UserResponse and return it 
		return userEntityList.stream().map(ur -> new UserResponse(ur)).collect(Collectors.toList());
	}

	/**
	 * 
	 * @return all the user names
	 * @throws NoUserInformationException if there is no information about any user then this exception is thrown
	 */
	public List<String> getUserNames() throws NoUserInformationException {

		List<String> userNames = userRepository.getUserNames();

		if (userNames.isEmpty())
			throw new NoUserInformationException("No Users information");

		return userNames;
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
