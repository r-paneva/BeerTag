package com.beertag.android.views.home;

import android.graphics.Bitmap;
import com.beertag.android.models.User;

public interface HomeContracts {

    interface View {

        void setPresenter(Presenter presenter);

        void showProgressBar();

        void hideProgressBar();

        void showError(Throwable error);

        void showMessage(String message);

        void showUserName(String name);

        void showOptionToChooseImage();

        void presentOptionToTakePicture();

        void showUserImage(Bitmap userImage);

        void showBeersListActivity();

        void showLoginActivity();
    }

    interface Presenter {

        void subscribe(View view);

        void unsubscribe();

        void loadUserInformation();

        void setUserName(String userName);

        void setUserId(int userId);

        void selectPictureFromGalleryButtonClickIsClicked();

        void takePictureButtonIsClicked();

        void newImageIsChosen(Bitmap image);

        void updateUserPicture(User user, String imageString);

        void decodeImageAndPresentToView(String userPicture, String errorMessage);

        int setPictureBtnVisability();

        void handleBeersButtonClick();

        void handleLoginButtonClick();

    }
    interface Navigator {

        void navigateToBeersListActivity();

        void navigateToLoginActivity();

    }
}