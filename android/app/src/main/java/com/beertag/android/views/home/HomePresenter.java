package com.beertag.android.views.home;

import android.graphics.Bitmap;

import com.beertag.android.async.base.SchedulerProvider;
import com.beertag.android.models.User;
import com.beertag.android.repositories.base.BitmapCacheRepository;
import com.beertag.android.services.base.UsersService;
import com.beertag.android.utils.Constants;
import com.beertag.android.utils.ImageEncoder;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class HomePresenter implements HomeContracts.Presenter {

    private final UsersService mUsersService;
    private final SchedulerProvider mSchedulerProvider;
    private final ImageEncoder mImageEncoder;
    private final BitmapCacheRepository mBitmapCacheRepository;
    private HomeContracts.View mView;

    private String mUserName;

    @Inject
    HomePresenter(UsersService usersService, SchedulerProvider schedulerProvider, ImageEncoder imageEncoder, BitmapCacheRepository bitmapCacheRepository) {
        mUsersService = usersService;
        mSchedulerProvider = schedulerProvider;
        mImageEncoder = imageEncoder;
        mBitmapCacheRepository = bitmapCacheRepository;
    }

    @Override
    public void subscribe(HomeContracts.View view) {
        mView = view;
    }

      @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void setUserName(String userName) {
        mUserName = userName;
    }

    @Override
    public void selectPictureFromGalleryButtonClickIsClicked() {
        mView.showOptionToChooseImage();
    }

    @Override
    public void takePictureButtonIsClicked() {
        mView.presentOptionToTakePicture();
    }

    @Override
    public void newImageIsChosen(Bitmap image) {
        String imageString = mImageEncoder.encodeBitmapToString(image);

        if (Objects.equals(imageString, null)) {
            mView.showMessage(Constants.IMAGE_CHANGE_ERROR_MESSAGE);
        } else {
            mView.showProgressBar();
            Disposable observable = Observable
                    .create((ObservableOnSubscribe<User>) emitter -> {
                        User user = mUsersService.getUserByUserName(mUserName);
                        emitter.onNext(user);
                        emitter.onComplete();
                    })
                    .subscribeOn(mSchedulerProvider.background())
                    .observeOn(mSchedulerProvider.ui())
                    .doFinally(mView::hideProgressBar)
                    .subscribe(userResult -> updateUserPicture(userResult, imageString),
                            error -> mView.showError(error));
        }

    }

    @Override
    public void updateUserPicture(User user, String imageString) {
        if (!Objects.equals(user, null)) {
            user.setImage(imageString);
            mView.showProgressBar();
            Disposable observable = Observable
                    .create((ObservableOnSubscribe<User>) emitter -> {
                        mUsersService.updateUser(user);
                        emitter.onNext(user);
                        emitter.onComplete();
                    })
                    .subscribeOn(mSchedulerProvider.background())
                    .observeOn(mSchedulerProvider.ui())
                    .doFinally(mView::hideProgressBar)
                    .subscribe(userResult -> {
                                if (Objects.equals(userResult.getImage(), null)) {
                                    mView.showMessage(Constants.IMAGE_CHANGE_ERROR_MESSAGE);
                                } else {
                                    decodeImageAndPresentToView(userResult.getImage(), Constants.IMAGE_CHANGE_ERROR_MESSAGE);
                                }
                            },
                            error -> mView.showError(error));
        } else {
            mView.showMessage(Constants.IMAGE_CHANGE_ERROR_MESSAGE);
        }
    }

    @Override
    public void decodeImageAndPresentToView(String userPicture, String errorMessage) {

        Bitmap decodedUserPicture = mImageEncoder.decodeStringToBitmap(userPicture);
        if (Objects.equals(decodedUserPicture, null)) {
            mView.showMessage(errorMessage);
        } else {
            mView.showUserImage(decodedUserPicture);
            mBitmapCacheRepository.addBitmapToBitmapCache(decodedUserPicture, Constants.USER_IMAGE_KEY);
        }
    }

    @Override
    public void loadUserInformation() {
        mView.showProgressBar();
        Disposable observable = Observable
                .create((ObservableOnSubscribe<User>) emitter -> {
                    User user = mUsersService.getUserByUserName(mUserName);
                    emitter.onNext(user);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideProgressBar)
                .subscribe(userResult -> {
                            if (!Objects.equals(userResult.getImage(), null)) {
                                decodeImageAndPresentToView(userResult.getImage(), Constants.ERROR_LOADING_USER_IMAGE);
                            }
                            String fullName = userResult.getFirstName() + " " + userResult.getLastName();
                            mView.showUserName(fullName);
                        },
                        error -> mView.showError(error));
    }

    @Override
    public int setPictureBtnVisability(){
        if (Objects.equals(mUserName, "")){
            return GONE;
        }else{
            return VISIBLE;
        }
    }

}
