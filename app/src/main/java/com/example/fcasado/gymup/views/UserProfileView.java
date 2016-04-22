package com.example.fcasado.gymup.views;

import com.example.fcasado.gymup.data.User;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by fcasado on 4/22/16.
 */
public interface UserProfileView extends MvpView {
	// display user name
	void showUserName(String firstName, String lastName);

	// display user profile updated
	void showUserProfileUpdated(User user);
}
