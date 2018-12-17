package com.beertag.android.diconfig;

import android.app.Application;

import com.beertag.android.BeerTagApplication;
import com.beertag.android.diconfig.viewmodules.GetPictureModule;
import com.beertag.android.diconfig.viewmodules.HomeModule;
import com.beertag.android.diconfig.viewmodules.ImageViewModule;
import com.beertag.android.diconfig.viewmodules.LoginModule;
import com.beertag.android.diconfig.viewmodules.MyBeersModule;
import com.beertag.android.diconfig.viewmodules.ViewsModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ActivityBindingModule.class,
        AppModule.class,
        AsyncModule.class,
        HomeModule.class,
        HttpModule.class,
        ImageViewModule.class,
        GetPictureModule.class,
        LoginModule.class,
        MyBeersModule.class,
        ParsersModule.class,
        RepositoriesModule.class,
        ServicesModule.class,
        UtilitiesModule.class,
        ViewsModule.class,
        AndroidSupportInjectionModule.class
})

public interface AppComponent extends AndroidInjector<BeerTagApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}

