package com.example.fcasado.gymup.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fcasado.gymup.R;
import com.example.fcasado.gymup.data.User;
import com.example.fcasado.gymup.presenters.UserProfilePresenter;
import com.example.fcasado.gymup.views.UserProfileView;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileActivity extends MvpActivity<UserProfileView, UserProfilePresenter> implements UserProfileView {

	@Bind(R.id.et_first_name)
	EditText etFirstName;
	@Bind(R.id.et_last_name)
	EditText etLastName;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		ButterKnife.bind(this);
		initViews();
	}

	private void initViews() {
		getPresenter().getUserName();
	}

	@NonNull
	@Override
	public UserProfilePresenter createPresenter() {
		return new UserProfilePresenter();
	}

	@Override
	public void showUserName(String firstName, String lastName) {
		etFirstName.setText(firstName);
		etLastName.setText(lastName);
	}

	@Override
	public void showUserProfileUpdated(User user) {
		if (user == null) {
			Toast.makeText(this, "Error updating user", Toast.LENGTH_SHORT).show();
			return;
		}

		showUserName(user.getFirstName(), user.getLastName());
	}

	@OnClick(R.id.bt_update_user_name)
	public void onUpdateUserNameClicked() {
		getPresenter().updateUserName(etFirstName.getText().toString(), etLastName.getText().toString());
	}

}
