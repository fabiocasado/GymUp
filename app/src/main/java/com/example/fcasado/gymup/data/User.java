package com.example.fcasado.gymup.data;

/**
 * Created by fcasado on 4/18/16.
 */
public class User {
	private String uid;
	private String firstName;
	private String lastName;

	public User() {
	}

	public User(String uid, String firstName, String lastName) {
		this.uid = uid;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUid() {
		return uid;
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
