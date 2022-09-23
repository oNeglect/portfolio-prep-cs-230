package com.dropwizard.GameAuth.resources.auth;

import java.security.Principal;
import java.util.Set;

//class which implements principal, which is used to construct the roles for the server to gain access.
//after gaining access, the list is populated by user id and name, which are constructed on the server.
public class GameUser implements Principal {
	//declaring variables
	private String name = "";
	
	private final Set<String> roles;
	//constructor for all game users with name and role
	public GameUser(String name) {
		this.name = name;
		this.roles = null;
	}
	//constructor for users names and the set of roles
	public GameUser(String name, Set<String> roles) {
		this.name = name;
		this.roles = roles;
	}
	//process to gain name of the object name
	public String getName() {
		return name;
	}
	//process to gain id of specific name in the set roles
	public int getId() {
		return (int) (Math.random() * 100);
	}
	//process to gain the set of roles
	public Set<String> getRoles() {
		return roles;
	}
}
