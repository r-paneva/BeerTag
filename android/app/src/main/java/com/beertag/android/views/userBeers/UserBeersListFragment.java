package com.beertag.android.views.userBeers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beertag.android.R;
import com.beertag.android.models.UserBeers;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserBeersListFragment
        extends Fragment
        implements UserBeersListContracts.View, UserBeersAdapter.OnBeerClickListener {
    private UserBeersListContracts.Navigator mNavigator;

    @BindView(R.id.rv_beers)
    RecyclerView mView;

    @BindView(R.id.loading)
    ProgressBar mLoadingView;

    @Inject
    UserBeersAdapter mUserBeersAdapter;

    private UserBeersListContracts.Presenter mPresenter;

    private int mUserId;

    @Inject
    public UserBeersListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancBeer) {
        View view = inflater.inflate(R.layout.fragment_my_beers_list, container, false);

        // ButterKnife is applied
        ButterKnife.bind(this, view);

        mView.setAdapter(mUserBeersAdapter);
        mUserBeersAdapter.setOnBeerClickListener(this);
        LinearLayoutManager mBeersViewLayoutManager = new LinearLayoutManager(getContext());
        mView.setLayoutManager(mBeersViewLayoutManager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
        mPresenter.loadUserBeers(mUserId);
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
    public void setPresenter(UserBeersListContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showUserBeers(List<UserBeers> userbeers) {
        mUserBeersAdapter.clear();
        mUserBeersAdapter.addAllUserBeers(userbeers);
        mUserBeersAdapter.notifyDataSetChanged();
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
    public void showBeerDetails(UserBeers userBeers) {
        mNavigator.navigateWith(userBeers);
    }

    @Override
    public void hideLoading() {
        mView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    void setNavigator(UserBeersListContracts.Navigator navigator) {
        mNavigator = navigator;
    }

    @Override
    public void onClick(UserBeers userBeers) {
        mPresenter.selectBeer(userBeers);
    }
}