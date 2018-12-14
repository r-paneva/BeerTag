package com.beertag.android.diconfig;

import com.beertag.android.repositories.LruBitmapCacheRepository;
import com.beertag.android.repositories.base.BitmapCacheRepository;
import com.beertag.android.utils.ImageEncoder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilitiesModule {

    @Provides
    @Singleton
    public ImageEncoder imageEncoder() {
        return new ImageEncoder();
    }


    @Provides
    @Singleton
    public BitmapCacheRepository bitmapCacheRepository() {
        return new LruBitmapCacheRepository();
    }

}
