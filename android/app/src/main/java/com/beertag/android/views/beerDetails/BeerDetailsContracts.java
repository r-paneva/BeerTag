package com.beertag.android.views.beerDetails;


import android.graphics.Bitmap;

import com.beertag.android.models.Beer;
import com.beertag.android.models.User;

public interface BeerDetailsContracts {
    interface View {
        void showBeer(Beer beer);

        void setPresenter(Presenter presenter);

        void showError(Throwable e);

        void showMessage(String message);

        void showLoading();

        void hideLoading();

        void hideLoading(Beer beer);

        void showDefaultBeerPicture();

        void showBeerImage(Bitmap image);

        void presentOptionToTakePicture();

        void showOptionToChooseImage();

    }

    interface Presenter {
        void subscribe(View view);

        void unsubscribe();

        void loadBeer();

        void setBeerId(int beerId);

        void selectPictureFromGalleryButtonClickIsClicked();

        void takePictureButtonIsClicked();

        void setRating(User whoRates, Beer rated, int stars);

        void updateBeer(Beer beer);

        void newImageIsChosen(Bitmap image);

        void decodeImageAndPresentToView(Beer beer);

        void updateBeerPicture(Beer beer, String imageString);

        void setUserId(int userId);

    }
    interface Navigator {
        // void navigateToHome();
    }
}