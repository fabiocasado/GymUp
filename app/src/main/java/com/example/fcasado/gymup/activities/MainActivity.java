package com.example.fcasado.gymup.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fcasado.gymup.GymUp;
import com.example.fcasado.gymup.R;
import com.example.fcasado.gymup.callbacks.ILoginListener;
import com.example.fcasado.gymup.callbacks.IStorageCompletion;
import com.example.fcasado.gymup.callbacks.IUserRetriever;
import com.example.fcasado.gymup.data.User;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {
	private CallbackManager callbackManager;
	private AccessTokenTracker facebookAccessTokenTracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startFacebookComponents();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		stopFacebookComponents();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);

		menu.findItem(R.id.remove_user).setVisible(GymUp.currentUser != null);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.remove_user: {
				GymUp.remoteStorage.unregisterUser(GymUp.currentUser, new IStorageCompletion() {
					@Override
					public void onStorageError() {
						Toast.makeText(MainActivity.this, "Error unregistering user", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onStorageSuccess() {
						GymUp.currentUser = null;
						LoginManager.getInstance().logOut();
						invalidateOptionsMenu();
					}
				});
			}

			default:
		}

		return true;
	}

	private void startFacebookComponents() {
		callbackManager = CallbackManager.Factory.create();
		facebookAccessTokenTracker = new AccessTokenTracker() {
			@Override
			protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
				onFacebookAccessTokenChange(currentAccessToken);
			}
		};

		LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
		loginButton.setReadPermissions("user_friends");
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
			}

			@Override
			public void onCancel() {

			}

			@Override
			public void onError(FacebookException error) {

			}
		});

		if (AccessToken.getCurrentAccessToken() != null) {
			onFacebookAccessTokenChange(AccessToken.getCurrentAccessToken());
		}
	}

	private void stopFacebookComponents() {
		if (facebookAccessTokenTracker != null) {
			facebookAccessTokenTracker.stopTracking();
		}
	}

	private void onFacebookAccessTokenChange(AccessToken accessToken) {
		final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
		dialog.setIndeterminate(true);
		dialog.show();

		GymUp.remoteStorage.authUser(accessToken, new ILoginListener() {
			@Override
			public void onLogin() {
				GymUp.remoteStorage.getUserData(GymUp.currentUser.getUid(), new IUserRetriever() {
					@Override
					public void onUserRetrieved(User user) {
						if (user == null) {
							Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
							startActivity(registerIntent);
						} else {
							Toast.makeText(MainActivity.this, "Greet " + user.getFirstName() + " " + user.getLastName(), Toast.LENGTH_SHORT).show();
						}

						dialog.dismiss();
						invalidateOptionsMenu();
					}

					@Override
					public void onError() {
						Toast.makeText(MainActivity.this, "Error getting user data", Toast.LENGTH_SHORT).show();

						dialog.dismiss();
					}
				});
			}

			@Override
			public void onLogout() {
				Toast.makeText(MainActivity.this, "See you soon!", Toast.LENGTH_SHORT).show();
				GymUp.currentUser = null;
				dialog.dismiss();
				invalidateOptionsMenu();
			}

			@Override
			public void onError() {
				Toast.makeText(MainActivity.this, "User login failed.", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
}
