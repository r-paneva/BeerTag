package com.beertag.android;

import com.beertag.android.diconfig.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BeerTagApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
       return DaggerAppComponent.builder().application(this).build();
//       return null;
    }
}

