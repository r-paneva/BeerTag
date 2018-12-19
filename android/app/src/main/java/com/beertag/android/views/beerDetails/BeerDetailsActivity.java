package com.beertag.android.views.beerDetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.beertag.android.R;
import com.beertag.android.models.Beer;
import com.beertag.android.utils.Constants;
import com.beertag.android.views.BaseDrawerActivity;
import com.beertag.android.views.login.LoginActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.beertag.android.utils.Constants.PREFERENCES_BEER_ID_KEY;
import static com.beertag.android.utils.Constants.PREFERENCES_BEER_NAME_KEY;

public class BeerDetailsActivity extends BaseDrawerActivity implements BeerDetailsContracts.Navigator {

    @Inject
    BeerDetailsFragment mBeerDetailsFragment;

    @Inject
    BeerDetailsContracts.Presenter mBeerDetailsPresenter;

    @Inject
    public BeerDetailsActivity() {
    }

    private static Context mContext;
    private SharedPreferences mPreferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);

        mContext = getApplicationContext();

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Beer beer = (Beer) intent.getSerializableExtra(Constants.BEER_EXTRA_KEY);
        persistBeerSessionData(beer);

        mBeerDetailsPresenter.setBeerId(beer.getId());
        mBeerDetailsFragment.setPresenter(mBeerDetailsPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mBeerDetailsFragment)
                .commit();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected long getIdentifier() {
        return Constants.DETAILS_IDENTIFIER;
    }

    private void persistBeerSessionData(Beer beer) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREFERENCES_BEER_ID_KEY, beer.getId());
        editor.putString(PREFERENCES_BEER_NAME_KEY, beer.getName());
        editor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (shouldStartSignIn()) {
//            startSignIn();
//        }
    }

    private boolean shouldStartSignIn() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        return mBeerDetailsPresenter.setUserId() == 0;
    }

    private void startSignIn() {
        startActivity(new Intent(this, LoginActivity.class));
    }

}
