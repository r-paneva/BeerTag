package com.beertag.android.views.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beertag.android.R;
import com.beertag.android.models.User;
import com.beertag.android.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class LoginFragment extends Fragment implements LoginContracts.View {

    private static final int REQUEST_CODE = 10;

    @BindView(R.id.et_username_field)
    EditText mUserNameEditText;

    @BindView(R.id.tv_login_credentials_problem)
    TextView mLoginProblemTextView;

    @BindView(R.id.prb_loading_view)
    ProgressBar mProgressBarView;

//    @BindView(R.id.btn_login)
//    Button mLoginButton;


    private LoginContracts.Presenter mPresenter;
    private LoginContracts.Navigator mNavigator;
    private String mUser;


    @Inject
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void setPresenter(LoginContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    public void setNavigator(LoginContracts.Navigator navigator) {
        mNavigator = navigator;
    }

    @Override
    public void showLoginCredentialsProblemMessage(String message) {
        mLoginProblemTextView.setVisibility(View.VISIBLE);
        mLoginProblemTextView.setText(message);
    }

    @Override
    public void hideLoginProblemMessage() {
        mLoginProblemTextView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mProgressBarView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBarView.setVisibility(View.GONE);
    }


    @OnClick(R.id.btn_login)
    public void onLoginButtonClick() {
        mPresenter.getUserByUserName(mUserNameEditText.getText().toString());
    }


    @OnFocusChange(R.id.et_username_field)
    public void onUsernameFieldFocusChange() {
        mPresenter.handleLoginFieldFocusChange(mLoginProblemTextView.getVisibility());
    }

    @Override
    public void showError(Throwable error) {
        String errorMessage = Constants.ERROR_MESSAGE + error.getMessage();
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showHomeActivityWithUser(User user) {
        mNavigator.navigateToHomeWithUser(user);
    }

    @Override
    public void showMessage(String message) {

        Toast
                .makeText(getContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @OnTextChanged(R.id.et_username_field)
    public void onUserNameEditTextChanged() {
        mPresenter.checkErrorVisibility(mLoginProblemTextView.getVisibility());
    }

}
