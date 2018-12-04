package com.beertag.android.views.beersList;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beertag.android.R;
import com.beertag.android.models.Beer;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class BeersListFragment
        extends Fragment
        implements BeersListContracts.View, BeersAdapter.OnBeerClickListener {
    private BeersListContracts.Navigator mNavigator;

    @BindView(R.id.rv_beers)
    RecyclerView mBeersView;

    @BindView(R.id.loading)
    ProgressBar mLoadingView;

    @BindView(R.id.et_filter)
    EditText mFilterEditText;

    @Inject
    BeersAdapter mBeersAdapter;

    private BeersListContracts.Presenter mPresenter;
    private LinearLayoutManager mBeersViewLayoutManager;
    String mPattern;

    @Inject
    public BeersListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancBeer) {
        View view = inflater.inflate(R.layout.fragment_beers_list, container, false);

        // ButterKnife is applied
        ButterKnife.bind(this, view);

        mBeersView.setAdapter(mBeersAdapter);
        mBeersAdapter.setOnBeerClickListener(this);
        mBeersViewLayoutManager = new LinearLayoutManager(getContext());
        mBeersView.setLayoutManager(mBeersViewLayoutManager);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
        mPresenter.loadBeers();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(BeersListContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showBeers(List<Beer> beers) {
        if (beers.size() == 1 && mPattern == null) {
            onClick(beers.get(0));
        } else {
            mBeersAdapter.clear();
            mBeersAdapter.addAll(beers);
            mBeersAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void showEmptyBeersList() {
        Toast.makeText(getContext(),
                "No Beers",
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showLoading() {
        mBeersView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBeerDetails(Beer beer) {
        mNavigator.navigateWith(beer);
    }

    @Override
    public void hideLoading() {
        mBeersView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    void setNavigator(BeersListContracts.Navigator navigator) {
        mNavigator = navigator;
    }

    @OnTextChanged(R.id.et_filter)
    public void onTextChanged() {
        mPattern = mFilterEditText.getText().toString();
        mPresenter.filterBeers(mPattern);
    }

    @Override
    public void onClick(Beer beer) {
        mPresenter.selectBeer(beer);
    }
}