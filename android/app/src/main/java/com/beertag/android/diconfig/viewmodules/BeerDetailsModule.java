package com.beertag.android.diconfig.viewmodules;

import com.beertag.android.diconfig.ActivityScoped;
import com.beertag.android.diconfig.FragmentScoped;
import com.beertag.android.views.beerDetails.BeerDetailsContracts;
import com.beertag.android.views.beerDetails.BeerDetailsFragment;
import com.beertag.android.views.beerDetails.BeerDetailsPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class BeerDetailsModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract BeerDetailsFragment BeerDetailsFragment();

    @ActivityScoped
    @Binds
    abstract BeerDetailsContracts.Presenter BeerDetailsPresenter(BeerDetailsPresenter presenter);
}