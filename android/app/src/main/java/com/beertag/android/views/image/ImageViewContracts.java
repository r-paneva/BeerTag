package com.beertag.android.views.image;

import android.graphics.Bitmap;

public interface ImageViewContracts {

    interface View {

        void setPresenter(Presenter presenter);

        void showMessage(String message);

        void showImage(Bitmap image);
    }

    interface Presenter {

        void subscribe(View view);

        void unsubscribe();

        void setImage(String imageString);

        void loadImage();
    }
}
