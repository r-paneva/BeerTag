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
import com.beertag.android.models.MyBeers;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        mPresenter.loadMyBeers(mUserId);
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
    public void showMyBeers(List<MyBeers> mybeers) {
        mMyBeersAdapter.clear();
        mMyBeersAdapter.addAllMyBeers(mybeers);
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
    public void showBeerDetails(MyBeers myBeers) {
        mNavigator.navigateWith(myBeers);
    }

    @Override
    public void hideLoading() {
        mView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    void setNavigator(MyBeersListContracts.Navigator navigator) {
        mNavigator = navigator;
    }

    @Override
    public void onClick(MyBeers myBeers) {
        mPresenter.selectBeer(myBeers);
    }
}