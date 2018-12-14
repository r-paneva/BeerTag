package com.beertag.android.diconfig.viewmodules;


import com.beertag.android.diconfig.ActivityScoped;
import com.beertag.android.diconfig.FragmentScoped;
import com.beertag.android.views.home.HomeContracts;
import com.beertag.android.views.home.HomeFragment;
import com.beertag.android.views.home.HomePresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomeFragment homeFragment();

    @ActivityScoped
    @Binds
    abstract HomeContracts.Presenter homeActivityPresenter(HomePresenter homePresenter);

}
