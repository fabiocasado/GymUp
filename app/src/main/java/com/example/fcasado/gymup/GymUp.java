package com.example.fcasado.gymup;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by fcasado on 4/18/16.
 */
public class GymUp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		Firebase.setAndroidContext(this);
	}
}
