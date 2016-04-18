package com.example.fcasado.gymup.storage.firebase;

import android.support.annotation.NonNull;

import com.example.fcasado.gymup.callbacks.IOnUsersRetrieved;
import com.example.fcasado.gymup.callbacks.IStorageCompletion;
import com.example.fcasado.gymup.data.User;
import com.example.fcasado.gymup.storage.IRemoteStorage;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fcasado on 4/18/16.
 */
public class StorageFirebase implements IRemoteStorage {
	private static final String ROOT_REF = "https://popping-heat-9850.firebaseio.com/web/data";
	private static final String USERS_REF = "users";

	Firebase rootRef;

	public StorageFirebase() {
		rootRef = new Firebase(ROOT_REF);
	}

	@Override
	public void saveUser(User user, final IStorageCompletion completionCallback) {
		Firebase userRef = rootRef.child(USERS_REF).child("1");
		userRef.setValue(user, new Firebase.CompletionListener() {
			@Override
			public void onComplete(FirebaseError firebaseError, Firebase firebase) {
				if (firebaseError != null) {
					completionCallback.onStorageError();
				} else {
					completionCallback.onStorageSuccess();
				}
			}
		});
	}

	@Override
	public void getUsers(@NonNull final IOnUsersRetrieved onUsersRetrieved) {
		Firebase usersRef = rootRef.child(USERS_REF);
		usersRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				List<User> users = new ArrayList<>();

				for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
					User user = userSnapshot.getValue(User.class);
					users.add(user);
				}

				onUsersRetrieved.onUsersRetrieved(users);
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {
				onUsersRetrieved.onUsersRetrieved(null);
			}
		});
	}
}
