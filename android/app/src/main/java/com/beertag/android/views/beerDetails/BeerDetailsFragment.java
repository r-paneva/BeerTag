package com.beertag.android.views.beerDetails;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beertag.android.R;
import com.beertag.android.models.Beer;
import com.beertag.android.models.Drink;
import com.beertag.android.models.UserBeers;
import com.beertag.android.models.User;
import com.beertag.android.utils.Constants;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeerDetailsFragment
        extends android.support.v4.app.Fragment
        implements BeerDetailsContracts.View, ReviewListener, BeerDetailsContracts.Navigator {


    private BeerDetailsContracts.Presenter mPresenter;
    private BeerDetailsContracts.Navigator mNavigator;

    private static final int GALLERY_IMAGE_CHOOSER_REQUEST_CODE = 7;
    private static final int TAKE_PICTURE_REQUEST_CODE = 2;

    @BindView(R.id.prb_loading)
    ProgressBar mProgressBarView;

    @BindView(R.id.tv_name)
    TextView mNameTextView;

    @BindView(R.id.iv_beer_details)
    ImageView mBeerImageView;

    @BindView(R.id.tv_description)
    TextView mDescriptionTextView;

    @BindView(R.id.tv_brewery)
    TextView mBreweryTextView;

    @BindView(R.id.tv_country)
    TextView mCountryTextView;

    @BindView(R.id.tv_ABV)
    TextView mABVTextView;

    @BindView(R.id.tv_do_drink)
    TextView mDrinkTextView;

    @BindView(R.id.tv_style)
    TextView mStyleTextView;

    @BindView(R.id.tv_tag)
    TextView mTagTextView;

    @BindView(R.id.rating_beer)
    RatingBar mBeerRatingBar;

    @BindView(R.id.tv_rate_beer)
    TextView mRateBeer;

    @BindView(R.id.tv_want_drink)
    TextView mWantDrink;

    @BindView(R.id.tv_drank)
    TextView mDrank;

    @BindView(R.id.fab_take_picture)
    FloatingActionButton mTakePictureButton;

    @BindView(R.id.fab_change_picture)
    FloatingActionButton mChangePictureButton;

    @BindView(R.id.fam_image_options_menu)
    FloatingActionsMenu mImageChangeFloatingMenu;

    View mView;
    ReviewListener mReviewListener;
    User user;
    Beer beer;
    Drink drink;
    int mBeerId;
    UserBeers mUserBeers;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_beer_details, container, false);
        ButterKnife.bind(this, rootView);
        mView = rootView;
        hideKeyboardFrom(Objects.requireNonNull(getContext()), mView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
        mPresenter.loadBeer();
        mPresenter.loadUser();
        hideKeyboardFrom(Objects.requireNonNull(getContext()), mView);
    }

    @Override
    public void onStart() {
        super.onStart();
        handler.post(runnableCode);
        hideKeyboardFrom(Objects.requireNonNull(getContext()), mView);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setPresenter(BeerDetailsContracts.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void showBeer(Beer beer) {
        mReviewListener = this;
        mBeerId = beer.getId();

        mNameTextView.setText(beer.getName().toUpperCase());

        if (Objects.equals(beer.getImage(), null) || beer.getImage().length() <= 2) {
            showDefaultBeerPicture();
        } else {
            InputStream stream = new ByteArrayInputStream(Base64.decode(beer.getImage().getBytes(), Base64.DEFAULT));
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            mBeerImageView.setImageBitmap(bitmap);
        }

        mDescriptionTextView.setText(String.format("Description : %s", beer.getDescription()));
        mDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        mBreweryTextView.setText(String.format("Brewery : %s", beer.getBrewery()));
        mCountryTextView.setText(String.format("Country : %s", beer.getCountry().getName()));
        mABVTextView.setText(String.format("Alcohol by Volume : %s%%", beer.getAlcohol()));
        mStyleTextView.setText(String.format("Style : %s", beer.getStyle().getName()));

        // rating bar
        if (beer.getTag() != null) {
            mTagTextView.setText(String.format("Tag : %s", beer.getTag().getName()));
        }
        mBeerRatingBar.setRating(beer.getRating());
        if (mPresenter.loadUserId() != 0) {
            mRateBeer.setText("RATE BEER");
            mRateBeer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = getContext();
                    FiveStarsDialog fiveStarsDialog = new FiveStarsDialog(context);
                    fiveStarsDialog.setRateText("How many stars this beer deserves?")
                            .setTitle("")
                            .setStarColor(Color.YELLOW)
                            .setReviewListener(mReviewListener) // Used to listen for reviews (if you want to track them )
                            .showAfter(0);
                }
            });

            mWantDrink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drink = new Drink(1,"I want to drink it");
                    mPresenter.setRating(beer.getId(), user.getId(), drink, beer, 0);
                }


            });

            mDrank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drink = new Drink(2,"I drank this beer");
                    mPresenter.setRating(beer.getId(), user.getId(), drink, beer, 0);
                }
            });

        } else {
            showMessage("You must be logged in to rate beer");
        }

        if (mPresenter.loadUserId() != 0) {
            mPresenter.loadUserBeers(mPresenter.loadBeerId(), mPresenter.loadUserId());

            System.out.println("*******************************************************************************************************************");

            System.out.println(mPresenter.loadBeerId());

            System.out.println(mPresenter.loadUserId());
//            System.out.println(mUserBeers.getDrink());
            System.out.println("*******************************************************************************************************************");
//            mDrinkTextView.setText( mUserBeers.getDrink().toString());
        }else{
            mDrinkTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
        mProgressBarView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBarView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading(Beer beer) {
        Toast.makeText(getContext(), "Information updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUser(User User) {
        user = User;
    }

    @Override
    public void setBeer(Beer Beer) {
        beer = Beer;
    }

    @Override
    public void setUserBeers(UserBeers userBeers) {
        mUserBeers = userBeers;
    }

    @Override
    public void onReview(int stars) {
        drink = new Drink(0, "No");
        mPresenter.setRating(beer.getId(), user.getId(), drink, beer, stars);
    }

    @Override
    public void showDefaultBeerPicture() {
        mBeerImageView.setImageResource(R.drawable.defaultbeerpicture);
    }

    @Override
    public void showBeerImage(Bitmap beerImage) {
        mBeerImageView.setImageBitmap(beerImage);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @OnClick(R.id.fab_change_picture)
    public void onSelectPictureFromGalleryButtonClick() {
        mImageChangeFloatingMenu.collapse();
        mPresenter.selectPictureFromGalleryButtonClickIsClicked();
    }

    @OnClick(R.id.fab_take_picture)
    public void onTakePictureButtonClick() {
        mImageChangeFloatingMenu.collapse();
        mPresenter.takePictureButtonIsClicked();
    }

    @Override
    public void presentOptionToTakePicture() {
        Intent intentToTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentToTakePicture, TAKE_PICTURE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (!Objects.equals(data.getData(), null)) {
                Uri uri = data.getData();
                Bitmap image;
                try {
                    image = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), uri);
                    mPresenter.newImageIsChosen(image);
                    showMessage("Taken from gallery");
                } catch (IOException e) {
                    showError(e);
                }
            } else {
                showMessage("Error!!! ");
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if ((!Objects.equals(data, null)) && (!Objects.equals(data.getExtras(), null))) {
                Bitmap image;
                try {
                    image = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    mPresenter.newImageIsChosen(image);
                    showMessage("Photo was captured");
                } catch (NullPointerException npe) {
                    showError(npe);
                }
            } else {
                showMessage("Error!!! ");
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        String errorMessage = Constants.ERROR_MESSAGE + error.getMessage();
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOptionToChooseImage() {
        Intent intentToChooseImage = new Intent(Intent.ACTION_GET_CONTENT);
        intentToChooseImage.setType(Constants.IMAGE_FILE_TYPE);
        startActivityForResult(intentToChooseImage, GALLERY_IMAGE_CHOOSER_REQUEST_CODE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    public void setNavigator(BeerDetailsContracts.Navigator navigator) {
        mNavigator = navigator;
    }

}