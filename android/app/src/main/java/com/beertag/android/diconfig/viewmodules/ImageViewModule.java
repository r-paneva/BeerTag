package com.beertag.android.diconfig.viewmodules;

import com.beertag.android.diconfig.ActivityScoped;
import com.beertag.android.diconfig.FragmentScoped;
import com.beertag.android.views.image.ImageViewContracts;
import com.beertag.android.views.image.ImageViewFragment;
import com.beertag.android.views.image.ImageViewPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ImageViewModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ImageViewFragment imageViewFragment();

    @ActivityScoped
    @Binds
    abstract ImageViewContracts.Presenter imageViewPresenter(ImageViewPresenter imageViewPresenter);
}
