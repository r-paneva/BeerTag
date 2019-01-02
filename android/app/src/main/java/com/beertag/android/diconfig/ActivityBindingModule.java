package com.beertag.android.diconfig;

import com.beertag.android.diconfig.viewmodules.BeerCreateModule;
import com.beertag.android.diconfig.viewmodules.BeerDetailsModule;
import com.beertag.android.diconfig.viewmodules.BeersListModule;
import com.beertag.android.diconfig.viewmodules.GetPictureModule;
import com.beertag.android.diconfig.viewmodules.HomeModule;
import com.beertag.android.diconfig.viewmodules.ImageViewModule;
import com.beertag.android.diconfig.viewmodules.LoginModule;
import com.beertag.android.diconfig.viewmodules.UserBeersModule;
import com.beertag.android.views.home.HomeActivity;
import com.beertag.android.views.image.ImageViewActivity;
import com.beertag.android.views.login.LoginActivity;
import com.beertag.android.views.beerCreate.BeerCreateActivity;
import com.beertag.android.views.beerDetails.BeerDetailsActivity;
import com.beertag.android.views.beersList.BeersListActivity;
import com.beertag.android.views.getPicture.GetPictureActivity;
import com.beertag.android.views.userBeers.UserBeersListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = BeersListModule.class)
    abstract BeersListActivity BeersListActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = BeerDetailsModule.class)
    abstract BeerDetailsActivity BeerDetailsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = BeerCreateModule.class)
    abstract BeerCreateActivity BeerCreateActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity LoginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ImageViewModule.class)
    abstract ImageViewActivity imageViewActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = GetPictureModule.class)
    abstract GetPictureActivity getPictureActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity homeActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = UserBeersModule.class)
    abstract UserBeersListActivity UserBeersListActivity();

}