package com.meraphorce.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meraphorce.controllers.pojos.UserRequest;
import com.meraphorce.controllers.pojos.UserResponse;
import com.meraphorce.exceptions.NoUserInformationException;
import com.meraphorce.exceptions.UserAlreadyExistsException;
import com.meraphorce.exceptions.UserNotFoundException;
import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Validator validator;

	/**
	 * 
	 * @param userRequest - POJO that contains the user info
	 * @return
	 */
	public UserResponse createUser(UserRequest userRequest) {

		log.info("Validating user´s existence");
		User userEntity = userRepository.getUserByEmail(userRequest.getEmail().trim());

		if (userEntity == null) {
			log.info("Starting to save user");
			// Serialize the request into a User entity
			userEntity = new User(userRequest);
			log.info("Saving user");
			// Do persistence 
			userRepository.save(userEntity);
			log.info("User saved. returning User...");
		}
		else
			throw new UserAlreadyExistsException(String.format("the user´s email %s already exists", userRequest.getEmail()));

		// Serialize the User entity into a UserResponse and return it
		UserResponse ur = new UserResponse(userEntity);
		ur.setCreatedDate(Calendar.getInstance().getTime());

		return ur;
	}

	public UserResponse updateUser(String id, UserRequest userRequest) {

		User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found, nothing to change"));
		userEntity.setEmail(userRequest.getEmail());
		userEntity.setName(userRequest.getName());
		userRepository.save(userEntity);
		UserResponse ur = new UserResponse(userEntity);

		return ur;
	}

	/**
	 * 
	 * @param users - a list of users to save
	 * @return the saved users
	 */
	@Transactional
	public List<UserResponse> createUsersByBulk(List<UserRequest> users) {

		List<UserResponse> usersWithError = new ArrayList<UserResponse>();
		List<UserResponse> usersProcessed = new ArrayList<UserResponse>();
		Iterator<UserRequest> it = users.iterator();

		while (it.hasNext()) {

			UserRequest us = (UserRequest) it.next();
			Set<ConstraintViolation<UserRequest>> errors = validator.validate(us);

			if (!errors.isEmpty()) {
				List<String> errormessages = errors.stream().map(e -> e.getMessage()).collect(Collectors.toList());
				UserResponse ur = new UserResponse(us, new ArrayList<String>(errormessages));
				usersWithError.add(ur);
				it.remove();
				continue;
			}

			User userEntity = userRepository.getUserByEmail(us.getEmail());

			if (userEntity != null) {
				List<String> errorsList = new ArrayList<String>();
				errorsList.add("User already exists");
				UserResponse ur = new UserResponse(us, errorsList);
				usersWithError.add(ur);
				it.remove();
			}

		}

		log.info("Starting to save user list, serializing into User entity");
		List<User> userEntityList = users.stream().map(u -> new User(u)).collect(Collectors.toList());
		log.debug(String.format("Entities gotten: \n %s", userEntityList.toString()));
		log.info("Saving users...");
		userRepository.saveAll(userEntityList);
		log.info("Users saved, returning...");
		usersProcessed = userEntityList.stream().map(ur -> new UserResponse(ur)).collect(Collectors.toList());
		usersProcessed.forEach(us -> us.setCreatedDate(Calendar.getInstance().getTime()));
		usersProcessed.addAll(usersWithError);

		return usersProcessed;
	}

	public List<UserResponse> getAllUsers() {

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
	public List<String> getUserNames() {

		List<String> userNames = userRepository.getUserNames();

		if (userNames.isEmpty())
			throw new NoUserInformationException("No Users information");

		return userNames;
	}

	public UserResponse getUserByID(String userId) {

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
