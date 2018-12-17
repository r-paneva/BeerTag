package com.beertag.android.diconfig.viewmodules;

import android.support.v7.widget.RecyclerView;

import com.beertag.android.views.beersList.BeersAdapter;
import com.beertag.android.views.myBeers.MyBeersAdapter;


import dagger.Module;
import dagger.Provides;

@Module
public class ViewsModule {
    @Provides
    public RecyclerView.Adapter<BeersAdapter.BeerViewHolder> BeerArrayAdapter() {
        return new BeersAdapter();
    }

    @Provides
    public RecyclerView.Adapter<MyBeersAdapter.BeerViewHolder> MyBeersArrayAdapter() {
        return new MyBeersAdapter();
    }
}