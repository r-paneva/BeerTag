package com.beertag.android.views.beersList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.beertag.android.R;
import com.beertag.android.models.Beer;
import com.beertag.android.utils.Constants;
import com.beertag.android.views.BaseDrawerActivity;
import com.beertag.android.views.beerDetails.BeerDetailsActivity;
import com.beertag.android.views.beerDetails.BeerDetailsFragment;
import com.beertag.android.views.beerDetails.BeerDetailsPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class BeersListActivity extends BaseDrawerActivity implements BeersListContracts.Navigator {

    @Inject
    BeersListFragment mBeersListFragment;

    @Inject
    BeersListContracts.Presenter mBeersListPresenter;

    @Inject
    BeerDetailsFragment mBeerDetailsFragment;

    @Inject
    BeerDetailsPresenter mBeerDetailsPresenter;


    private static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();

        setContentView(R.layout.activity_beers_list);

        ButterKnife.bind(this);

        //setSupportActionBar(getToolbar());

        mBeersListFragment.setNavigator(this);
        mBeersListFragment.setPresenter(mBeersListPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mBeersListFragment)
                .commit();
    }

    public static Context getAppContext() {
        return mContext;
    }

    private boolean isPhone() {
        return findViewById(R.id.content_details) == null;
    }

    @Override
    protected long getIdentifier() {
        return Constants.LIST_IDENTIFIER;
    }

    @Override
    public void navigateWith(Beer beer) {
        if (isPhone()) {
            Intent intent = new Intent(this, BeerDetailsActivity.class);
            intent.putExtra(Constants.BEER_EXTRA_KEY, beer);
            startActivity(intent);
            mBeerDetailsPresenter.setBeerId(beer.getId());
        } else {
            mBeerDetailsPresenter.setBeerId(beer.getId());
            mBeerDetailsPresenter.loadBeer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}