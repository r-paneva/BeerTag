package com.beertag.android.views.myBeers;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class MyBeersListFragment
        extends Fragment
        implements MyBeersListContracts.View, MyBeersAdapter.OnBeerClickListener {
    private MyBeersListContracts.Navigator mNavigator;

    @BindView(R.id.rv_beers)
    RecyclerView mView;

    @BindView(R.id.loading)
    ProgressBar mLoadingView;

    @BindView(R.id.et_filter)
    EditText mFilterEditText;

    @Inject
    MyBeersAdapter mMyBeersAdapter;

    private MyBeersListContracts.Presenter mPresenter;
    String mPattern;

    private int mUserId;

    @Inject
    public MyBeersListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancBeer) {
        View view = inflater.inflate(R.layout.fragment_my_beers_list, container, false);

        // ButterKnife is applied
        ButterKnife.bind(this, view);


        mView.setAdapter(mMyBeersAdapter);
        mMyBeersAdapter.setOnBeerClickListener(this);
        LinearLayoutManager mBeersViewLayoutManager = new LinearLayoutManager(getContext());
        mView.setLayoutManager(mBeersViewLayoutManager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
        mPresenter.loadBeers(mUserId);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void setUserId(int userId) {
        mUserId = userId;
    }

    @Override
    public void setPresenter(MyBeersListContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showBeers(List<Beer> beers) {
        mMyBeersAdapter.clear();
        mMyBeersAdapter.addAll(beers);
        mMyBeersAdapter.notifyDataSetChanged();
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
        mView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBeerDetails(Beer beer) {
        mNavigator.navigateWith(beer);
    }

    @Override
    public void hideLoading() {
        mView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    void setNavigator(MyBeersListContracts.Navigator navigator) {
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