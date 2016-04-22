package com.example.fcasado.gymup.storage.firebase;

import com.example.fcasado.gymup.GymUp;
import com.example.fcasado.gymup.callbacks.ILoginListener;
import com.example.fcasado.gymup.callbacks.IStorageCompletion;
import com.example.fcasado.gymup.callbacks.IUserProfileUpdated;
import com.example.fcasado.gymup.callbacks.IUserRetriever;
import com.example.fcasado.gymup.data.User;
import com.example.fcasado.gymup.storage.IRemoteStorage;
import com.example.fcasado.gymup.utils.Constants;
import com.facebook.AccessToken;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by fcasado on 4/18/16.
 */
public class StorageFirebase implements IRemoteStorage {
	public static final String APP_REF = "https://popping-heat-9850.firebaseio.com";
	private static final String USERS_REF = "users";

	Firebase rootRef;

	public StorageFirebase() {
		rootRef = new Firebase(APP_REF);
	}

	@Override
	public void authUser(AccessToken accessToken, final ILoginListener loginListener) {
		if (accessToken != null) {
			rootRef.authWithOAuthToken("facebook", accessToken.getToken(), new Firebase.AuthResultHandler() {
				@Override
				public void onAuthenticated(AuthData authData) {
					// The Facebook user is now authenticated with your Firebase app
					Map<String, Object> providerData = (Map<String, Object>) authData.getProviderData().get(Constants.FACEBOOK_CACHED_USER_PROFILE);
					String first_name = (String) providerData.get(Constants.FACEBOOK_FIRST_NAME);
					String last_name = (String) providerData.get(Constants.FACEBOOK_LAST_NAME);

					GymUp.currentUser = new User(authData.getUid(), first_name, last_name);

					loginListener.onLogin();
				}

				@Override
				public void onAuthenticationError(FirebaseError firebaseError) {
					loginListener.onError();
				}
			});
		} else {
			/* Logged out of Facebook so do a logout from the Firebase app */
			rootRef.unauth();
			loginListener.onLogout();
		}
	}

	@Override
	public void getUserData(String uid, final IUserRetriever retriever) {
		final Firebase usersRef = rootRef.child(USERS_REF).child(GymUp.currentUser.getUid());
		usersRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				User user = null;

				if (dataSnapshot.getValue() != null) {
					user = dataSnapshot.getValue(User.class);
				}

				retriever.onUserRetrieved(user);
				usersRef.removeEventListener(this);
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {
				retriever.onError();
				usersRef.removeEventListener(this);
			}
		});
	}

	@Override
	public void registerUser(User user, final IStorageCompletion callback) {
		Firebase userRef = rootRef.child(USERS_REF).child(user.getUid());
		userRef.setValue(user, new Firebase.CompletionListener() {
			@Override
			public void onComplete(FirebaseError firebaseError, Firebase firebase) {
				if (firebaseError != null) {
					callback.onStorageError();
				} else {
					callback.onStorageSuccess();
				}
			}
		});
	}

	@Override
	public void unregisterUser(User user, final IStorageCompletion callback) {
		Firebase userRef = rootRef.child(USERS_REF).child(user.getUid());
		userRef.removeValue(new Firebase.CompletionListener() {
			@Override
			public void onComplete(FirebaseError firebaseError, Firebase firebase) {
				if (firebaseError != null) {
					callback.onStorageError();
				} else {
					callback.onStorageSuccess();
				}
			}
		});
	}

	@Override
	public void updateUserProfile(final User user, final IUserProfileUpdated callback) {
		Firebase userRef = rootRef.child(USERS_REF).child(user.getUid());
		userRef.setValue(user, new Firebase.CompletionListener() {
			@Override
			public void onComplete(FirebaseError firebaseError, Firebase firebase) {
				if (firebaseError != null) {
					callback.onError();
				} else {
					callback.onSuccess(user);
				}
			}
		});
	}
}
