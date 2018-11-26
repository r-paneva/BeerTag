package com.beertag.android.views.beerDetails;


import android.app.Activity;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beertag.android.R;
import com.beertag.android.models.Beer;
import com.beertag.android.models.User;
import com.beertag.android.views.beersList.BeersListActivity;
import com.squareup.picasso.Picasso;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeerDetailsFragment
        extends Fragment
        implements BeerDetailsContracts.View, ReviewListener {


    private BeerDetailsContracts.Presenter mPresenter;

    @BindView(R.id.tv_name)
    TextView mNameTextView;

    @BindView(R.id.iv_beer_details)
    ImageView mImageView;

    @BindView(R.id.tv_description)
    TextView mDescriptionTextView;

    @BindView(R.id.tv_brewery)
    TextView mBreweryTextView;

    @BindView(R.id.tv_country)
    TextView mCountryTextView;

    @BindView(R.id.tv_ABV)
    TextView mABVTextView;

    @BindView(R.id.tv_style)
    TextView mStyleTextView;

    @BindView(R.id.tv_tag)
    TextView mTagTextView;

    @BindView(R.id.tv_beer_name)
    TextView mBeerName;

    @BindView(R.id.rating_beer)
    RatingBar mBeerRatingBar;

    @BindView(R.id.tv_rate_beer)
    TextView mRateBeer;

    View mView;
    ReviewListener mReviewListener;
    User user;
    Beer beer;
    private String mWhoRates;

    Handler handler = new Handler();
    // Define the code block to be executed
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            // Do something here on the main thread
            Log.d("Handlers", "Called on main thread");
            refresh();
            // Repeat this the same runnable code block again another 2 seconds
            handler.postDelayed(runnableCode, 5000);
        }

        private void refresh() {
            mPresenter.loadBeer();
        }
    };

    @Inject
    public BeerDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beer_details, container, false);
        ButterKnife.bind(this, rootView);
        mView = rootView;
        hideKeyboardFrom(getContext(), mView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
        mPresenter.loadBeer();
        hideKeyboardFrom(getContext(), mView);
    }

    @Override
    public void onStart() {
        super.onStart();
        handler.post(runnableCode);
        hideKeyboardFrom(getContext(), mView);
    }

    @Override
    public void showBeer(Beer beer) {
        mReviewListener = this;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(BeersListActivity.getAppContext());
        String username = sharedPrefs.getString("username", "");
        mNameTextView.setText(beer.getName());
        if(!beer.getImage().isEmpty()) {
            Picasso.get().load(beer.getImage()).into(mImageView);
        }
        mDescriptionTextView.setText("Description : " + beer.getDescription());
        mDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());

        mBreweryTextView.setText("Brewery : "+beer.getBrewery());
        mCountryTextView.setText("Country : "+beer.getOrigin());
        mABVTextView.setText("Alcohol by Volume : "+beer.getABV());
        mStyleTextView.setText("Style : "+beer.getStyle());
        mTagTextView.setText("Style : "+beer.getTag());
        mBeerRatingBar.setRating(beer.getRating());
        mRateBeer.setText("Rate beer");
        mRateBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context= getContext();
                FiveStarsDialog fiveStarsDialog = new FiveStarsDialog(context);//new FiveStarsDialog(getContext(),user);
                fiveStarsDialog.setRateText("How many stars this beer deserves?")
                        .setTitle("")
                        //.setForceMode(false)
                        .setStarColor(Color.YELLOW)
                        //.setUpperBound(2) // Market opened if a rating >= 2 is selected
                        //.setNegativeReviewListener(this) // OVERRIDE mail intent for negative review
                        .setReviewListener(mReviewListener) // Used to listen for reviews (if you want to track them )
                        .showAfter(0);
            }
        });
        //mRatingBar.setOnRatingBarChangeListener();
        }

    @Override
    public void setPresenter(BeerDetailsContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onReview(int stars) {//, User user) {
        mPresenter.setRating(user, beer, stars);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
