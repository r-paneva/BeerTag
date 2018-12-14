package com.beertag.android.views.beerCreate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beertag.android.R;
import com.beertag.android.models.Beer;
import com.beertag.android.models.Country;
import com.beertag.android.models.Style;
import com.beertag.android.models.Tag;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeerCreateFragment extends Fragment implements BeerCreateContracts.View, Validator.ValidationListener {

    private static final int TAKE_PICTURE_REQUEST_CODE = 1;

    private BeerCreateContracts.Presenter mPresenter;
    private BeerCreateContracts.Navigator mNavigator;
    private Validator mValidator;
    private Country mCountry;
    private Style mStyle;
    private Tag mTag;
    private Bitmap mPicture;

    List<String> mStyles = new ArrayList<>();
    List<String> mTags = new ArrayList<>();
    List<String> mCountries = new ArrayList<>();

    @BindView(R.id.prb_loading)
    ProgressBar mProgressBarView;

    @BindView(R.id.et_name)
    @Pattern(regex = "^[a-zA-Z0-9]{5,16}$", message = "Must be longer than 4 characters and contain no special symbols!")
    @NotEmpty
    EditText mName;

    @BindView(R.id.et_brewery)
    @Pattern(regex = "^[a-zA-Z0-9]{0,20}$", message = "Must not contain special characters!")
    @NotEmpty
    EditText mBrewery;

    @BindView(R.id.sp_country_of_origin)
    @NotEmpty
    MaterialSpinner mCountryOfOrigin;

    @BindView(R.id.et_abv)
    @NotEmpty
    @Pattern(regex = "^([+-]?\\d*\\.?\\d*)$", message = "Must be a float number! ")
    EditText mABV;

    @BindView(R.id.et_description)
    @Length(max = 200, message = "Must be less than 200 characters")
    EditText mDescription;

    @BindView(R.id.sp_style)
    @NotEmpty
    MaterialSpinner mStyleSpinner;

    @BindView(R.id.sp_tag)
    MaterialSpinner mTagSpinner;

    @BindView(R.id.fab_new_image)
    com.getbase.floatingactionbutton.FloatingActionButton newImageButton;

    @BindView(R.id.iv_beer_image)
    ImageView newImageView;

    @Inject
    public BeerCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beer_create, container, false);
        ButterKnife.bind(this, view);

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mValidator.validate();
            }
        });

        mBrewery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mValidator.validate();
            }
        });

        mABV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mValidator.validate();
            }
        });

        mDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mValidator.validate();
            }
        });

        mPresenter.loadCountries();
        mPresenter.loadStyles();
        mPresenter.loadTags();

        mCountryOfOrigin.setItems(mCountries);
        mCountryOfOrigin.setOnItemSelectedListener(
                (MaterialSpinner.OnItemSelectedListener<String>) (view1, position, id, item) -> {
                    Snackbar.make(view1, "Selected country " + item, Snackbar.LENGTH_LONG).show();
                    mPresenter.loadCountry(item);
                });
        mCountryOfOrigin.setOnNothingSelectedListener(spinner -> Snackbar.make(spinner, "Please select country", Snackbar.LENGTH_LONG).show());

        mStyleSpinner.setItems(mStyles);
        mStyleSpinner.setOnItemSelectedListener(
                (MaterialSpinner.OnItemSelectedListener<String>) (view12, position, id, item) -> {
                    Snackbar.make(view12, "Selected style " + item, Snackbar.LENGTH_LONG).show();
                    mPresenter.loadStyle(item);

                });
        mStyleSpinner.setOnNothingSelectedListener(spinner -> {
            Snackbar.make(spinner, "Please select style", Snackbar.LENGTH_LONG).show();
            mPresenter.loadStyle("");
        });

        mTagSpinner.setItems(mTags);
        mTagSpinner.setOnItemSelectedListener(
                (MaterialSpinner.OnItemSelectedListener<String>) (view13, position, id, item) -> {
                    Snackbar.make(view13, "Selected tag " + item, Snackbar.LENGTH_LONG).show();
                    mPresenter.loadTag(item);
                });
        mTagSpinner.setOnNothingSelectedListener(spinner -> {
            Snackbar.make(spinner, "No tag selected", Snackbar.LENGTH_LONG).show();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void showStyles(List<Style> styles) {
        for (Style s : styles) {
            mStyles.add(s.getName());
        }
    }

    @Override
    public void showTags(List<Tag> tags) {
        for (Tag t : tags) {
            mTags.add(t.getName());
        }
    }

    @Override
    public void showCountries(List<Country> countries) {
        for (Country c : countries) {
            mCountries.add(c.getName());
        }
    }

    @Override
    public void showEmptyList() {
        Toast.makeText(getContext(),
                "Can not load items",
                Toast.LENGTH_LONG)
                .show();
    }

    @OnClick(R.id.btn_save)
    public void onBeerSaveClicked() {

        try {
            Beer beer;
            if (mTag == null) {
                mTag = new Tag();
                mTag.setId(1);
                mTag.setName("");
            }
            String pictureString = "";
            if (mPicture != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                mPicture.compress(Bitmap.CompressFormat.PNG, 25, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                pictureString = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }
            String name = mName.getText().toString();
            String brewery = mBrewery.getText().toString();
            String alcohol = mABV.getText().toString();
            String description = mDescription.getText().toString();
            beer = new Beer(
                    name,
                    brewery,
                    alcohol,
                    description,
                    pictureString,
                    mCountry,
                    mTag,
                    mStyle
            );

            mPresenter.save(beer);

        } catch (Exception e) {

            Toast.makeText(getContext(), "Please fill out all required fields"
                    , Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void setCountry(Country country) {
        mCountry = country;
    }

    @Override
    public void setStyle(Style style) {
        mStyle = style;
    }

    @Override
    public void setTag(Tag tag) {
        mTag = tag;
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void setPresenter(BeerCreateContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void navigateToHome() {
        mNavigator.navigateToHome();
    }

    @Override
    public void showLoading() {
        mProgressBarView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBarView.setVisibility(View.GONE);
    }

    public void setNavigator(BeerCreateContracts.Navigator navigator) {
        mNavigator = navigator;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onValidationSucceeded() {
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.fab_new_image)
    public void onTakePictureButtonClick() {
        mPresenter.takePictureButtonIsClicked();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap picture = (Bitmap) data.getExtras().get("data");//this is your bitmap image and now you can do whatever you want with this
            newImageView.setImageBitmap(picture); //for example I put bmp in an ImageView
            mPicture = picture;
        }

    }

    @Override
    public void presentOptionToTakePicture() {
        Intent intentToTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentToTakePicture, TAKE_PICTURE_REQUEST_CODE);
    }

}
