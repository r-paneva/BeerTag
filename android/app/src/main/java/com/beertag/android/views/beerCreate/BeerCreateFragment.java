package com.beertag.android.views.beerCreate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeerCreateFragment extends Fragment implements BeerCreateContracts.View, Validator.ValidationListener {
    private BeerCreateContracts.Presenter mPresenter;
    private BeerCreateContracts.Navigator mNavigator;
    private String mCountryname;
    private Country mCountry;
    private Style mStyle;
    private Tag mTag;

    List<String> mStyles = new ArrayList<>();
    List<String> mTags = new ArrayList<>();
    List<String> mCountries = new ArrayList<>();

    @BindView(R.id.et_name)
    @Pattern(regex = "^[\\p{L} .'-]+$", message = "Must not contain special characters!")
    @NotEmpty
    EditText mName;

    @BindView(R.id.et_brewery)
    @Pattern(regex = "^(\\-?\\d+(?:\\.\\d+)?)%$", message = "Must not contain special characters!")
    EditText mBrewery;

    @BindView(R.id.sp_country_of_origin)
    @NotEmpty
    MaterialSpinner mCountryOfOrigin;

    @BindView(R.id.et_abv)
    @NotEmpty
    @Pattern(regex = "^(?:[0-9] ?){6,14}[0-9]$", message = "Must be float number! ")
    EditText mABV;

    @BindView(R.id.et_description)
    @Length(max = 200, message = "Must be less than 200 characters")
    EditText mDescription;

    @BindView(R.id.sp_style)
    @NotEmpty
    MaterialSpinner mStyleSpinner;

    @BindView(R.id.sp_tag)
    MaterialSpinner mTagSpinner;

    //**************************************************************************
//    @BindView(R.id.btn_newimage_camera)
//    Button newImageButton;
//
//
//    @BindView(R.id.iv_newimage_picture)
//    ImageView newImageView;
//
//    @BindView(R.id.tv_newimage_text)
//    TextView newImageTextTextView;

    @BindView(R.id.et_imageUrl)
    EditText mImage;

    //BitmapParser mChangeParser;
    private Validator mValidator;

    @Inject
    public BeerCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        mCountryOfOrigin.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mCountryname = item;
                Snackbar.make(view, "Selected country " + item, Snackbar.LENGTH_LONG).show();
                mPresenter.loadCountry(mCountryname);
            }

        });
        mCountryOfOrigin.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Please select country", Snackbar.LENGTH_LONG).show();
            }
        });

        mStyleSpinner.setItems(mStyles);
        mStyleSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Selected style " + item, Snackbar.LENGTH_LONG).show();
                mPresenter.loadStyle(item);

            }
        });
        mStyleSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Please select style", Snackbar.LENGTH_LONG).show();
                mPresenter.loadStyle("");
            }
        });

        mTagSpinner.setItems(mTags);
        mTagSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Selected tag " + item, Snackbar.LENGTH_LONG).show();
                mPresenter.loadTag(item);
            }
        });
        mTagSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "No tag selected", Snackbar.LENGTH_LONG).show();
                    mTag = new Tag();
                    mTag.setId(1);
                    mTag.setName("");
            }
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
            if(mTag == null){
                mTag = new Tag();
                mTag.setId(1);
                mTag.setName("");
            }

            String name = mName.getText().toString();
            String brewery = mBrewery.getText().toString();
            String alcohol = mABV.getText().toString();
            String description = mDescription.getText().toString();
            String image = mImage.getText().toString();

            Beer beer = new Beer(
                    name,
                    brewery,
                    alcohol,
                    description,
                    image,
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
    public void hideLoading() {

    }

    @Override
    public void showLoading() {
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
}
