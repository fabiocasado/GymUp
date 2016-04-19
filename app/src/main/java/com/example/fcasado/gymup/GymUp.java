package com.example.fcasado.gymup;

import android.app.Application;

import com.example.fcasado.gymup.data.User;
import com.example.fcasado.gymup.storage.IRemoteStorage;
import com.example.fcasado.gymup.storage.firebase.StorageFirebase;
import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;

/**
 * Created by fcasado on 4/18/16.
 */
public class GymUp extends Application {
	public static User currentUser;
	public static IRemoteStorage remoteStorage;

	@Override
	public void onCreate() {
		super.onCreate();

		FacebookSdk.sdkInitialize(getApplicationContext());
		Firebase.setAndroidContext(this);
		remoteStorage = new StorageFirebase();
	}
}
