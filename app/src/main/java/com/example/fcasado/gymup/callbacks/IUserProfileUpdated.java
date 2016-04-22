package com.example.fcasado.gymup.callbacks;

import com.example.fcasado.gymup.data.User;

/**
 * Created by fcasado on 4/22/16.
 */
public interface IUserProfileUpdated {
	void onError();

	void onSuccess(User user);
}
