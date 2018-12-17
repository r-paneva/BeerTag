package com.beertag.android.diconfig.viewmodules;

import com.beertag.android.diconfig.ActivityScoped;
import com.beertag.android.diconfig.FragmentScoped;
import com.beertag.android.views.myBeers.MyBeersListContracts;
import com.beertag.android.views.myBeers.MyBeersListFragment;
import com.beertag.android.views.myBeers.MyBeersListPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyBeersModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MyBeersListFragment MyBeersListFragment();

    @ActivityScoped
    @Binds
    abstract MyBeersListContracts.Presenter MyBeersListPresenter(MyBeersListPresenter presenter);


}
