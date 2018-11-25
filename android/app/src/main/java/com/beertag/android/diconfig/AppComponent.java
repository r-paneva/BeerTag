package com.beertag.android.diconfig;

import android.app.Application;

import com.beertag.android.BeerTagApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        HttpModule.class,
        ParsersModule.class,
        AppModule.class,
        ActivityBindingModule.class,
        LoginModule.class,
        RepositoriesModule.class,
        AsyncModule.class,
        ServicesModule.class,
        ViewsModule.class
})

public interface AppComponent extends AndroidInjector<BeerTagApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}

