package com.example.fcasado.gymup.data;

/**
 * Created by fcasado on 4/18/16.
 */
public class User {
	private String firstName;
	private String lastName;

	public User() {
		// empty default constructor, necessary for Firebase to be able to deserialize blog posts
	}

	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


}
