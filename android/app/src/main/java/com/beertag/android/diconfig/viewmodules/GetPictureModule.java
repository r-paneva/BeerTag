package com.beertag.android.diconfig.viewmodules;

import com.beertag.android.diconfig.FragmentScoped;
import com.beertag.android.views.getPicture.GetPictureFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class GetPictureModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract GetPictureFragment getPictureFragment();

//    @ActivityScoped
//    @Binds
//    abstract GetPictureContracts.Presenter getPicturePresenter(GetPicturePresenter imageViewPresenter);
}
