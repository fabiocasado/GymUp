package com.example.fcasado.gymup.storage;

import android.support.annotation.NonNull;

import com.example.fcasado.gymup.callbacks.IOnUsersRetrieved;
import com.example.fcasado.gymup.callbacks.IStorageCompletion;
import com.example.fcasado.gymup.data.User;

/**
 * Created by fcasado on 4/18/16.
 */
public interface IRemoteStorage {
	void saveUser(User user, IStorageCompletion completionCallback);
	void getUsers(@NonNull IOnUsersRetrieved onUsersRetrieved);
}
