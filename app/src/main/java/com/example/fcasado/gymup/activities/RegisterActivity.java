package com.example.fcasado.gymup.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fcasado.gymup.GymUp;
import com.example.fcasado.gymup.R;
import com.example.fcasado.gymup.callbacks.IStorageCompletion;

public class RegisterActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		EditText etFirstName = (EditText) findViewById(R.id.et_first_name);
		EditText etLastName = (EditText) findViewById(R.id.et_last_name);
		Button btRegister = (Button) findViewById(R.id.bt_register);

		etFirstName.setText(GymUp.currentUser.getFirstName());
		etLastName.setText(GymUp.currentUser.getLastName());
		btRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				registerUser();
			}
		});
	}

	private void registerUser() {
		GymUp.remoteStorage.registerUser(GymUp.currentUser, new IStorageCompletion() {
			@Override
			public void onStorageError() {

				Toast.makeText(RegisterActivity.this, "Registration error", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onStorageSuccess() {
				Toast.makeText(RegisterActivity.this, "Welcome " + GymUp.currentUser.getFirstName() + " " + GymUp.currentUser.getLastName(), Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}
}
