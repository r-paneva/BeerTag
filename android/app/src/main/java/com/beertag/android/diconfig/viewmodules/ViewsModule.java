package com.beertag.android.diconfig.viewmodules;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.beertag.android.utils.ImageEncoder;
import com.beertag.android.views.beersList.BeersAdapter;


import dagger.Module;
import dagger.Provides;

@Module
public class ViewsModule {
    @Provides
    public RecyclerView.Adapter<BeersAdapter.BeerViewHolder> BeerArrayAdapter(ImageEncoder mImageEncoder) {
        return new BeersAdapter(mImageEncoder);
    }
}