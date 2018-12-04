package com.beertag.android.views.beerDetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.beertag.android.Constants;
import com.beertag.android.R;
import com.beertag.android.models.Beer;
import com.beertag.android.views.BaseDrawerActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class BeerDetailsActivity extends BaseDrawerActivity {

    @Inject
    BeerDetailsFragment mBeerDetailsFragment;

    @Inject
    BeerDetailsContracts.Presenter mBeerDetailsPresenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Beer beer = (Beer) intent.getSerializableExtra(Constants.EXTRA_KEY);

        mBeerDetailsPresenter.setBeerId(beer.getId());
        mBeerDetailsFragment.setPresenter(mBeerDetailsPresenter);

        getFragmentManager()
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

    //    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, BeerDetailsActivity.class);
        startActivity(intent);
        finish();
    }
}
