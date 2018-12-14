package com.beertag.android.utils.base;

import android.graphics.Bitmap;

public interface ImageEncoder {

    String encodeBitmapToString(Bitmap bitmap);

    Bitmap decodeStringToBitmap(String imageString);
}
