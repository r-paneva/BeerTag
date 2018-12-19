package com.beertag.android.views.home;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beertag.android.R;
import com.beertag.android.utils.Constants;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends Fragment implements HomeContracts.View {
    private static final int GALLERY_IMAGE_CHOOSER_REQUEST_CODE = 5;
    private static final int TAKE_PICTURE_REQUEST_CODE = 10;

    @BindView(R.id.iv_home_user_image)
    ImageView mUserPictureImageView;

    @BindView(R.id.tv_user_names)
    TextView mUserFullNameTextView;

    @BindView(R.id.prb_loading_view)
    ProgressBar mProgressBarView;

    @BindView(R.id.fab_change_picture)
    FloatingActionButton mChangePictureFloatingActionButton;

    @BindView(R.id.fab_take_picture)
    FloatingActionButton mTakePictureFloatingActionButton;

    @BindView(R.id.fam_image_options_menu)
    FloatingActionsMenu mImageChangeFloatingMenu;

    @BindView(R.id.btn_beers)
    Button mBeersButton;

    @BindView(R.id.btn_login)
    Button mLoginButton;

    private HomeContracts.Presenter mPresenter;
    private HomeContracts.Navigator mNavigator;

    @Inject
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mImageChangeFloatingMenu.setVisibility(mPresenter.setPictureBtnVisability());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
        mPresenter.loadUserInformation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(HomeContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar() {
        mProgressBarView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBarView.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable error) {
        String errorMessage = Constants.ERROR_MESSAGE + error.getMessage();
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showUserName(String name) {
        mUserFullNameTextView.setText(name);
    }

    @OnClick(R.id.fab_change_picture)
    public void onSelectPictureFromGalleryButtonClick() {
        mImageChangeFloatingMenu.collapse();
        mPresenter.selectPictureFromGalleryButtonClickIsClicked();
    }

    @OnClick(R.id.fab_take_picture)
    public void onTakePictureButtonClick() {

        mImageChangeFloatingMenu.collapse();
        mPresenter.takePictureButtonIsClicked();
    }

    @Override
    public void presentOptionToTakePicture() {
        Intent intentToTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentToTakePicture, TAKE_PICTURE_REQUEST_CODE);
    }

    @Override
    public void showOptionToChooseImage() {
        Intent intentToChooseImage = new Intent(Intent.ACTION_GET_CONTENT);
        intentToChooseImage.setType(Constants.IMAGE_FILE_TYPE);
        startActivityForResult(intentToChooseImage, GALLERY_IMAGE_CHOOSER_REQUEST_CODE);
    }

    @OnClick(R.id.btn_beers)
    public void onBeersButtonClick() {
        mPresenter.handleBeersButtonClick();
    }

    @OnClick(R.id.btn_login)
    public void onLoginButtonClick() {
        mPresenter.handleLoginButtonClick();
    }


    public void setNavigator(HomeContracts.Navigator navigator) {
        mNavigator = navigator;
    }

    @Override
    public void showBeersListActivity() {
        mNavigator.navigateToBeersListActivity();
    }

    @Override
    public void showLoginActivity() {
        mNavigator.navigateToLoginActivity();
    }


    @Override
    public void showUserImage(Bitmap userImage) {
        mUserPictureImageView.setImageBitmap(userImage);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (!Objects.equals(data.getData(), null)) {
                Uri uri = data.getData();
                Bitmap image;
                try {
                    image = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), uri);
                    mPresenter.newImageIsChosen(image);
                    showMessage("Taken from gallery");
                } catch (IOException e) {
                    showError(e);
                }
            } else {
                showMessage("Error!!! ");
            }
        }

        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if ((!Objects.equals(data, null)) && (!Objects.equals(data.getExtras(), null))) {
                Bitmap image;
                try {
                    image = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    mPresenter.newImageIsChosen(image);
                    showMessage("Photo was captured");
                } catch (NullPointerException npe) {
                    showError(npe);
                }
            } else {
                showMessage("Error!!! ");
            }
        }
    }
}
