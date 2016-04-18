package com.example.fcasado.gymup.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.fcasado.gymup.R;
import com.example.fcasado.gymup.callbacks.IOnUsersRetrieved;
import com.example.fcasado.gymup.callbacks.IStorageCompletion;
import com.example.fcasado.gymup.data.User;
import com.example.fcasado.gymup.storage.IRemoteStorage;
import com.example.fcasado.gymup.storage.firebase.StorageFirebase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	private IRemoteStorage remoteStorage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		remoteStorage = new StorageFirebase();
	}

	public void saveUserData(View view) {
		User user = new User("Fabio", "Casado");
		remoteStorage.saveUser(user, new IStorageCompletion() {
			@Override
			public void onStorageError() {
				Toast.makeText(MainActivity.this, "User not saved!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onStorageSuccess() {
				Toast.makeText(MainActivity.this, "User saved!", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void listUserData(View view) {
		remoteStorage.getUsers(new IOnUsersRetrieved() {
			@Override
			public void onUsersRetrieved(List<User> users) {
				if (users != null) {
					Toast.makeText(MainActivity.this, "There are " + users.size() + " users!", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "Error retrieving users.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
