package com.beertag.android.views.beerDetails;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.beertag.android.utils.Constants;
import com.beertag.android.R;
import com.beertag.android.models.Beer;
import com.beertag.android.views.BaseDrawerActivity;

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Beer beer = (Beer) intent.getSerializableExtra(Constants.EXTRA_KEY);
        persistBeerSessionData(beer);

        mBeerDetailsPresenter.setBeerId(beer.getId());
        mBeerDetailsFragment.setPresenter(mBeerDetailsPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mBeerDetailsFragment)
                .commit();
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

    @Override
    public void navigateToHome(){
        Intent intent = new Intent(this, BeerDetailsActivity.class);
        startActivity(intent);
        finish();
    }

    private void persistBeerSessionData(Beer beer) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREFERENCES_BEER_ID_KEY, beer.getId());
        editor.putString(PREFERENCES_BEER_NAME_KEY, beer.getName());
        editor.apply();
    }
}
