package com.example.fcasado.gymup.models;

import com.example.fcasado.gymup.GymUp;
import com.example.fcasado.gymup.callbacks.IUserProfileUpdated;
import com.example.fcasado.gymup.data.User;

/**
 * Created by fcasado on 4/22/16.
 */
public class UserProfileModel {
	private User user;

	public UserProfileModel() {
		user = GymUp.currentUser;
	}

	public String getFirstName() {
		return user.getFirstName();
	}

	public String getLastName() {
		return user.getLastName();
	}

	public void updateUserName(String firstName, String lastName, IUserProfileUpdated callback) {
		user.setFirstName(firstName);
		user.setLastName(lastName);

		GymUp.remoteStorage.updateUserProfile(user, callback);
	}
}
