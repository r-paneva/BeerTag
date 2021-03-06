package com.beertag.android.views.userBeers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.beertag.android.R;
import com.beertag.android.models.UserBeers;
import com.beertag.android.utils.Constants;
import com.beertag.android.views.BaseDrawerActivity;
import com.beertag.android.views.beerDetails.BeerDetailsActivity;
import com.beertag.android.views.beerDetails.BeerDetailsFragment;
import com.beertag.android.views.beerDetails.BeerDetailsPresenter;
import com.beertag.android.views.login.LoginActivity;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class UserBeersListActivity
        extends BaseDrawerActivity
        implements UserBeersListContracts.Navigator {


    @Inject
    UserBeersListFragment mUserBeersListFragment;

    @Inject
    UserBeersListContracts.Presenter mBeersListPresenter;

    @Inject
    BeerDetailsFragment mBeerDetailsFragment;

    @Inject
    BeerDetailsPresenter mBeerDetailsPresenter;

    @Inject
    UserBeersListPresenter mUserBeerListPresenter;

    SharedPreferences mSharedPreferences;
//    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (shouldStartSignIn()) {
            startSignIn();
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_beers_list);

        ButterKnife.bind(this);

        //setSupportActionBar(getToolbar());

        mUserBeersListFragment.setNavigator(this);
        mUserBeersListFragment.setPresenter(mBeersListPresenter);
        mUserBeersListFragment.setUserId(this.setUserId());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mUserBeersListFragment)
                .commit();

    }

    private boolean isPhone() {
        return findViewById(R.id.content_details) == null;
    }

    @Override
    protected long getIdentifier() {
        return Constants.MY_BEERS_IDENTIFIER;
    }

    @Override
    public void navigateWith(UserBeers userBeer) {
        if (isPhone()) {
            Intent intent = new Intent(this, BeerDetailsActivity.class);
            intent.putExtra(Constants.BEER_EXTRA_KEY, userBeer.getBeer());
            startActivity(intent);
            mBeerDetailsPresenter.setBeerId(userBeer.getBeer().getId());
        } else {
            mBeerDetailsPresenter.setBeerId(userBeer.getBeer().getId());
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
        return  mSharedPreferences.getInt(String.valueOf(R.string.userId),0);

    }

    private void startSignIn() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}