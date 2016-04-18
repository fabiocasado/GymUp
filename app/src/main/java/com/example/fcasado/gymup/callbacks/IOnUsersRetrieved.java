package com.example.fcasado.gymup.callbacks;

import com.example.fcasado.gymup.data.User;

import java.util.List;

/**
 * Created by fcasado on 4/18/16.
 */
public interface IOnUsersRetrieved {
	void onUsersRetrieved(List<User> users);
}
