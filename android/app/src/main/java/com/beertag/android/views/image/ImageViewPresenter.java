package com.beertag.android.views.image;

import android.graphics.Bitmap;

import com.beertag.android.utils.Constants;
import com.beertag.android.utils.ImageEncoder;

import java.util.Objects;

import javax.inject.Inject;

public class ImageViewPresenter implements ImageViewContracts.Presenter {

    private final ImageEncoder mImageEncoder;
    private ImageViewContracts.View mView;
    private String mImage;

    @Inject
    public ImageViewPresenter(ImageEncoder imageEncoder) {
        mImageEncoder = imageEncoder;
    }

    @Override
    public void subscribe(ImageViewContracts.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void setImage(String imageString) {
        mImage = imageString;
    }

    @Override
    public void loadImage() {
        Bitmap image = mImageEncoder.decodeStringToBitmap(mImage);

        if(Objects.equals(image,null)){
            mView.showMessage(Constants.ERROR_LOADING_IMAGE);
        }else {
            mView.showImage(image);
        }
    }
}
