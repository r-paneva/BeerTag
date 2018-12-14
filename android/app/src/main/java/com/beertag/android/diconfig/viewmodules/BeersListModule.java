package com.beertag.android.diconfig.viewmodules;


import com.beertag.android.diconfig.ActivityScoped;
import com.beertag.android.diconfig.FragmentScoped;
import com.beertag.android.views.beersList.BeersListContracts;
import com.beertag.android.views.beersList.BeersListFragment;
import com.beertag.android.views.beersList.BeersListPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BeersListModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract BeersListFragment BeersListFragment();

    @ActivityScoped
    @Binds
    abstract BeersListContracts.Presenter BeersListPresenter(BeersListPresenter presenter);
}
