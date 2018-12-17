package com.beertag.android.views.myBeers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.beertag.android.R;
import com.beertag.android.models.Beer;
import com.beertag.android.models.User;
import com.beertag.android.utils.Constants;
import com.beertag.android.views.BaseDrawerActivity;
import com.beertag.android.views.beerDetails.BeerDetailsActivity;
import com.beertag.android.views.beerDetails.BeerDetailsFragment;
import com.beertag.android.views.beerDetails.BeerDetailsPresenter;
import com.beertag.android.views.login.LoginActivity;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.beertag.android.utils.Constants.USER_EXTRA_KEY;

public class MyBeersListActivity
        extends BaseDrawerActivity
        implements MyBeersListContracts.Navigator {


    @Inject
    MyBeersListFragment mMyBeersListFragment;

    @Inject
    MyBeersListContracts.Presenter mBeersListPresenter;

    @Inject
    BeerDetailsFragment mBeerDetailsFragment;

    @Inject
    BeerDetailsPresenter mBeerDetailsPresenter;

    @Inject
    MyBeersListPresenter mMyBeerListPresenter;

    private static Context mContext;
    SharedPreferences mSharedPreferences;
//    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (shouldStartSignIn()) {
            startSignIn();
        }

        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();

        setContentView(R.layout.activity_my_beers_list);

        ButterKnife.bind(this);

        //setSupportActionBar(getToolbar());

        mMyBeersListFragment.setNavigator(this);
        mMyBeersListFragment.setPresenter(mBeersListPresenter);
        mMyBeersListFragment.setUserId(this.setUserId());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mMyBeersListFragment)
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
        return Constants.MY_BEERS_IDENTIFIER;
    }

    @Override
    public void navigateWith(Beer beer) {
        if (isPhone()) {
            Intent intent = new Intent(this, BeerDetailsActivity.class);
            intent.putExtra(Constants.EXTRA_KEY, beer);
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
            if (shouldStartSignIn()) {
                startSignIn();
            }
    }


    private boolean shouldStartSignIn() {
        mSharedPreferences = getSharedPreferences("com.beertag.android", Context.MODE_PRIVATE);
        return Objects.requireNonNull(mSharedPreferences.getString(String.valueOf(R.string.username), "")).isEmpty();

    }

    private int setUserId(){
        mSharedPreferences = getSharedPreferences("com.beertag.android", Context.MODE_PRIVATE);
        int id =  mSharedPreferences.getInt(String.valueOf(R.string.userId),0);
        return id;
    }

    private void startSignIn() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}