package com.beertag.android.diconfig;

import com.beertag.android.views.Login.LoginActivity;
import com.beertag.android.views.beerCreate.BeerCreateActivity;
import com.beertag.android.views.beerDetails.BeerDetailsActivity;
import com.beertag.android.views.beersList.BeersListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(
            modules = BeersListModule.class
    )
    abstract BeersListActivity BeersListActivity();

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = BeerDetailsModule.class
    )
    abstract BeerDetailsActivity BeerDetailsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = BeerCreateModule.class
    )
    abstract BeerCreateActivity BeerCreateActivity();


    @ActivityScoped
    @ContributesAndroidInjector(
            modules = LoginModule.class
    )
    abstract LoginActivity LoginActivity();

}