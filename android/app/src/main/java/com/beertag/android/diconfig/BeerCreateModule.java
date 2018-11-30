package com.beertag.android.diconfig;

import com.beertag.android.models.Country;
import com.beertag.android.views.beerCreate.BeerCreateContracts;
import com.beertag.android.views.beerCreate.BeerCreateFragment;
import com.beertag.android.views.beerCreate.BeerCreatePresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BeerCreateModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract BeerCreateFragment BeerCreateFragment();

    @ActivityScoped
    @Binds
    abstract BeerCreateContracts.Presenter BeerCreatePresenter(BeerCreatePresenter presenter);
}

