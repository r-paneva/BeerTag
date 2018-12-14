package com.beertag.android.views.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.beertag.android.R;
import com.beertag.android.models.User;
import com.beertag.android.views.home.HomeActivity;
import com.stepstone.apprating.StringValue;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

import static com.beertag.android.utils.Constants.USER_EXTRA;
import static com.beertag.android.utils.Constants.USER_PROFILE_IMAGE_KEY;

public class LoginActivity extends DaggerAppCompatActivity implements LoginContracts.Navigator {
    private static final String TAG = "LoginActivity";
    SharedPreferences sharedPreferences;

    @Inject
    LoginPresenter mPresenter;

    @Inject
    LoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

         mLoginFragment.setNavigator(this);
         mLoginFragment.setPresenter(mPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_login, mLoginFragment)
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void navigateToHomeWithUser(User user) {
        Intent intent = new Intent(this, HomeActivity.class);

        sharedPreferences = getSharedPreferences("com.beertag.android", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(R.string.username),user.getUserName());
        editor.putString(String.valueOf(R.string.fullname), user.getFirstName()+" "+user.getLastName());
        editor.putString(String.valueOf(R.string.userpicture), user.getImage());
        editor.commit();

        intent.putExtra(USER_EXTRA, user);
        startActivity(intent);
        finish();
    }
}
