package com.example.fcasado.gymup.presenters;

import com.example.fcasado.gymup.GymUp;
import com.example.fcasado.gymup.callbacks.IUserProfileUpdated;
import com.example.fcasado.gymup.data.User;
import com.example.fcasado.gymup.models.UserProfileModel;
import com.example.fcasado.gymup.views.UserProfileView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by fcasado on 4/22/16.
 */
public class UserProfilePresenter extends MvpBasePresenter<UserProfileView> {
	// Business model
	private UserProfileModel userProfileModel;

	public UserProfilePresenter() {
		userProfileModel = new UserProfileModel();
	}

	public void getUserName() {
		if (isViewAttached()) {
			getView().showUserName(userProfileModel.getFirstName(), userProfileModel.getLastName());
		}
	}

	public void updateUserName(String firstName, String lastName) {
		userProfileModel.updateUserName(firstName, lastName, new IUserProfileUpdated() {
			@Override
			public void onError() {
				if (isViewAttached()) {
					getView().showUserProfileUpdated(null);
				}
			}

			@Override
			public void onSuccess(User user) {
				GymUp.currentUser = user;
				getView().showUserProfileUpdated(GymUp.currentUser);
			}
		});
	}
}
