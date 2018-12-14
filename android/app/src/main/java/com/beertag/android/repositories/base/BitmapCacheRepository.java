package com.beertag.android.repositories.base;

import android.graphics.Bitmap;

public interface BitmapCacheRepository {
    void addBitmapToBitmapCache(Bitmap bitmap, String key);

    Bitmap getBitmapFromCache(String key);

}
