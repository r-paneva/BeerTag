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
        ActivityBindingModule.class,
        AppModule.class,
        AsyncModule.class,
        HttpModule.class,
        LoginModule.class,
        ParsersModule.class,
        RepositoriesModule.class,
        ServicesModule.class,
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

