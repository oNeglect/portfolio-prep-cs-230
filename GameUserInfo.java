package com.dropwizard.GameAuth.resources.representations;

//Employees in example
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

//info class which shows the info of all game users
public class GameUserInfo {

	@NotNull
	private Integer id;
	@NotBlank
	@Length(min = 2, max = 255)
	private String firstName;
	@NotBlank
	@Length(min = 2, max = 255)
	private String lastName;
	@Pattern(regexp = ".+@.+\\.[a-z]+")
	private String email;

	// default constructor of game user info class
	public GameUserInfo() {
	}

	// constuctor which sets id, firstname, lastname, and email fro the specified
	// game user
	public GameUserInfo(Integer id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	// process to gain the user id
	public Integer getId() {
		return id;
	}

	// process to set the user id
	public void setId(Integer id) {
		this.id = id;
	}

	// process to gain the first name of the user
	public String getFirstName() {
		return firstName;
	}

	// process to set the first name of user
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	// process to gain the last name of user
	public String getLastName() {
		return lastName;
	}

	// process to set the last name of user
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// process to gain email of user
	public String getEmail() {
		return email;
	}

	// process to set email of user
	public void setEmail(String email) {
		this.email = email;
	}

	// process which prings out the game user id, first name, last name, and email
	// in the proper format
	@Override
	public String toString() {
		return "GameUser [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
}
