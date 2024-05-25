package com.meraphorce.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.meraphorce.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query("SELECT u.name FROM User u ORDER BY u.name ASC")
	public List<String> getUserNames();

	@Query("FROM User u WHERE u.email = :email")
	public User getUserByEmail(String email);

}
