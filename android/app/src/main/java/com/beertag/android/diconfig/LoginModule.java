package com.beertag.android.diconfig;

import com.beertag.android.views.Login.LoginContracts;
import com.beertag.android.views.Login.LoginPresenter;


import dagger.Binds;
import dagger.Module;

@Module
public abstract class LoginModule {

//    @FragmentScoped
//    @ContributesAndroidInjector
//    abstract LoginModuleFragment LoginModuleFragment();

    @ActivityScoped
    @Binds
    abstract LoginContracts.Presenter LoginPresenter(LoginPresenter presenter);
}