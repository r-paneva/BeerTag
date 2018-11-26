package com.beertag.android.views.beerCreate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.beertag.android.Constants;
import com.beertag.android.R;
import com.beertag.android.models.Country;
import com.beertag.android.services.base.CountryService;
import com.beertag.android.views.BaseDrawerActivity;
import com.beertag.android.views.beersList.BeersListActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static android.R.layout.simple_spinner_dropdown_item;

public class BeerCreateActivity extends BaseDrawerActivity implements BeerCreateContracts.Navigator {
    ArrayAdapter<String> countryAdapter;
    List<Country> countries = new ArrayList<>();
    private CountryService mCountryService;

    @Inject
    BeerCreateFragment mView;

    @Inject
    BeerCreateContracts.Presenter mPresenter;

    public BeerCreateActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_create);
        ButterKnife.bind(this);

        mView.setPresenter(mPresenter);
        mView.setNavigator(this);

        countryAdapter = new ArrayAdapter<String>(
                this,
                simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.countries));

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mView)
                .commit();
    }

    @Override
    protected long getIdentifier() {
        return Constants.CREATE_IDENTIFIER;
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, BeersListActivity.class);
        startActivity(intent);
        finish();
    }
}
