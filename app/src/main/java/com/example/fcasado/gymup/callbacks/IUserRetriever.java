package com.example.fcasado.gymup.callbacks;

import com.example.fcasado.gymup.data.User;

/**
 * Created by fcasado on 4/19/16.
 */
public interface IUserRetriever {
	void onUserRetrieved(User user);

	void onError();
}
