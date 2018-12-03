package com.beertag.android.views.beerCreate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeerCreateFragment extends Fragment implements BeerCreateContracts.View {
    private BeerCreateContracts.Presenter mPresenter;
    private BeerCreateContracts.Navigator mNavigator;
    private Country mCountry;
    private Style mStyle;
    private Tag mTag;
    String countryName="";

    List<String> mStyles = new ArrayList<>();
    List<String> mTags = new ArrayList<>();
    List<String> mCountries = new ArrayList<>();

    @BindView(R.id.et_name)
    EditText mName;

    @BindView(R.id.et_brewery)
    EditText mBrewery;

    @BindView(R.id.sp_country_of_origin)
    MaterialSpinner mCountryOfOrigin;

    @BindView(R.id.et_abv)
//    @NotEmpty
//    @Pattern(regex = "^(?:[0-9] ?){6,14}[0-9]$", message = "Must be float number! ")
            EditText mABV;

    @BindView(R.id.et_description)
    EditText mDescription;

    @BindView(R.id.sp_style)
    MaterialSpinner mStyleSpinner;

    @BindView(R.id.sp_tag)
    MaterialSpinner mTagSpinner;

    @BindView(R.id.et_imageUrl)
    EditText mImage;

    @Inject
    public BeerCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beer_create, container, false);
        ButterKnife.bind(this, view);

        mPresenter.loadCountries();
        mPresenter.loadStyles();
        mPresenter.loadTags();

        mCountryOfOrigin.setItems(mCountries);
        mCountryOfOrigin.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                countryName = item;
                //mPresenter.loadCountry(item);
                Snackbar.make(view, "Selected country " + item
                        + " " + id + " "+ position + " "+ mCountry,
                        Snackbar.LENGTH_LONG).show();
//                Snackbar.make(view, "mCountryOfOrigin " + mCountryOfOrigin, Snackbar.LENGTH_LONG).show();
            }

        });
        mCountryOfOrigin.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "No country selected", Snackbar.LENGTH_LONG).show();
            }
        });

        mStyleSpinner.setItems(mStyles);
        mStyleSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Selected style " + item, Snackbar.LENGTH_LONG).show();

            }
        });
        mStyleSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "No style selected", Snackbar.LENGTH_LONG).show();
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
                mPresenter.loadTag("");
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

            mPresenter.loadCountry(countryName);

            String name = mName.getText().toString();
            String brewery = mBrewery.getText().toString();
            Country country = mCountry;
            String alcohol = mABV.getText().toString();
            String description = mDescription.getText().toString();
            Style style = mStyle;
            Tag tag = mTag;
            String image = mImage.getText().toString();

            Beer beer = new Beer(
                    name,
                    brewery,
                    alcohol,
                    description,
                    image,
                    country,
                    tag,
                    style
                    );

            System.out.println("********************************************************************");
            System.out.println("country " + country.getId());
            System.out.println("style " + style.getId());
            System.out.println("tag " + tag.getClass());
            System.out.println("********************************************************************");


            mPresenter.save(beer);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: " + e.getMessage()
                    , Toast.LENGTH_LONG)
                    .show();
        }
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

    @Override
    public String getCountryName(){
        return countryName;
    }

    @Override
    public void setCountry(Country country) {
        mCountry.setId(country.getId());
    }

    @Override
    public void setStyle(Style style) {
        mStyle.setName(style.getName());
        mStyle.setId(style.getId());
    }

    @Override
    public void setTag(Tag tag) {
        mTag.setName(tag.getName());
        mTag.setId(tag.getId());
    }

    public void setNavigator(BeerCreateContracts.Navigator navigator) {
        mNavigator = navigator;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
