package com.beertag.android.diconfig;

import com.beertag.android.utils.Constants;
import com.beertag.android.http.HttpRequester;
import com.beertag.android.http.OkHttpHttpRequester;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class HttpModule {
    @Provides
    public HttpRequester httpRequester() {
        return new OkHttpHttpRequester();
    }

    @Provides
    @Named("baseServerUrl")
    public String baseServerUrl() {
        return Constants.BASE_SERVER_URL;
    }
}
