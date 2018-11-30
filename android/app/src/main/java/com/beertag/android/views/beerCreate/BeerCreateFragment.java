package com.beertag.android.views.beerCreate;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.beertag.android.R;
import com.beertag.android.models.Beer;
import com.beertag.android.models.Country;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeerCreateFragment extends Fragment implements BeerCreateContracts.View {
    private BeerCreateContracts.Presenter mPresenter;
    private BeerCreateContracts.Navigator mNavigator;
    List<String> mCountries = new ArrayList<>();

    @BindView(R.id.et_name)
    EditText mName;

    @BindView(R.id.et_brewery)
    EditText mBrewery;

    @BindView(R.id.sp_country_of_origin)
    Spinner mCountryOfOrigin;

    @BindView(R.id.et_abv)
    EditText mABV;

    @BindView(R.id.et_description)
    EditText mDescription;

    @BindView(R.id.et_style)
    EditText mStyle;

    @BindView(R.id.et_tag)
    EditText mTag;

    @BindView(R.id.et_imageUrl)
    EditText mImage;

    @Inject
    public BeerCreateActivity mActivity;

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

//        List<String> bb = new ArrayList<>();
//        bb.add("sdfg");
//        bb.add("vbnghj");

        final ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>
                (this.getActivity(), android.R.layout.simple_spinner_dropdown_item, mCountries);

        countryAdapter.notifyDataSetChanged();
        mCountryOfOrigin.setAdapter(countryAdapter);

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
    public void showCountries(List<Country> countries) {
        for (Country c : countries) {
            mCountries.add(c.getName());
        }
    }

    @Override
    public void showEmptyCountryList() {
        Toast.makeText(getContext(),
                "Can not load countries",
                Toast.LENGTH_LONG)
                .show();
    }

    @OnClick(R.id.btn_save)


    public void onBeerSaveClicked() {
        try {
            String name = mName.getText().toString();
            String brewery = mBrewery.getText().toString();
            String origin = mCountryOfOrigin.getSelectedItem().toString();
            String abv = mABV.getText().toString();
            String description = mDescription.getText().toString();
            String style = mStyle.getText().toString();
            String tag = mTag.getText().toString();
            String image = mImage.getText().toString();

            Beer beer = new Beer(
                    name,
                    brewery,
                    origin,
                    abv,
                    description,
                    style,
                    image,
                    tag);

            mPresenter.save(beer);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG) // todo
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

    public void setNavigator(BeerCreateContracts.Navigator navigator) {
        mNavigator = navigator;
    }


}
