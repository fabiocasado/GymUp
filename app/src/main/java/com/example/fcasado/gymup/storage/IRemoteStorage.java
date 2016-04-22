package com.example.fcasado.gymup.storage;

import com.example.fcasado.gymup.callbacks.ILoginListener;
import com.example.fcasado.gymup.callbacks.IStorageCompletion;
import com.example.fcasado.gymup.callbacks.IUserProfileUpdated;
import com.example.fcasado.gymup.callbacks.IUserRetriever;
import com.example.fcasado.gymup.data.User;
import com.facebook.AccessToken;

/**
 * Created by fcasado on 4/18/16.
 */
public interface IRemoteStorage {
	void authUser(AccessToken accessToken, ILoginListener loginListener);
	void getUserData(String uid, IUserRetriever retriever);
	void registerUser(User user, IStorageCompletion callback);
	void unregisterUser(User user, IStorageCompletion callback);

	void updateUserProfile(User user, IUserProfileUpdated callback);
}
