package com.beertag.android.diconfig.viewmodules;

import com.beertag.android.diconfig.ActivityScoped;
import com.beertag.android.diconfig.FragmentScoped;
import com.beertag.android.views.userBeers.UserBeersListContracts;
import com.beertag.android.views.userBeers.UserBeersListFragment;
import com.beertag.android.views.userBeers.UserBeersListPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class UserBeersModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract UserBeersListFragment UserBeersListFragment();

    @ActivityScoped
    @Binds
    abstract UserBeersListContracts.Presenter UserBeersListPresenter(UserBeersListPresenter presenter);


}
