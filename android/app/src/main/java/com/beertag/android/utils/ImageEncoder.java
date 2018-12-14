package com.beertag.android.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ImageEncoder implements com.beertag.android.utils.base.ImageEncoder {

    @Override
    public String encodeBitmapToString(Bitmap bitmap) {

        String imageString;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, Constants.IMAGE_QUALITY, byteArrayOutputStream);
            byte[] imageArray = byteArrayOutputStream.toByteArray();

            imageString = Base64.encodeToString(imageArray, Base64.DEFAULT);
            return imageString;

        } catch (Exception e) {
            return null;
        } finally {
            if (!Objects.equals(byteArrayOutputStream, null)) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public Bitmap decodeStringToBitmap(String imageString) {
        Bitmap decodedImage;
        try {
            byte[] encodedBytes = Base64.decode(imageString, Base64.DEFAULT);
            decodedImage = BitmapFactory.decodeByteArray(encodedBytes, 0, encodedBytes.length);

            return decodedImage;

        } catch (Exception e) {
            return null;
        }
    }
}
