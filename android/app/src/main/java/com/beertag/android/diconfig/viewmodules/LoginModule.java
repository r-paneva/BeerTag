package com.beertag.android.diconfig.viewmodules;

import com.beertag.android.diconfig.ActivityScoped;
import com.beertag.android.diconfig.FragmentScoped;
import com.beertag.android.views.login.LoginContracts;
import com.beertag.android.views.login.LoginFragment;
import com.beertag.android.views.login.LoginPresenter;


import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoginModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoginFragment LoginModuleFragment();

    @ActivityScoped
    @Binds
    abstract LoginContracts.Presenter LoginPresenter(LoginPresenter presenter);
}