package com.beertag.android.diconfig;

import com.beertag.android.async.AsyncSchedulerProvider;
import com.beertag.android.async.base.SchedulerProvider;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AsyncModule {
    @Provides
    @Singleton
    public SchedulerProvider schedulerProvider() {
        return AsyncSchedulerProvider.getInstance();
    }
}
