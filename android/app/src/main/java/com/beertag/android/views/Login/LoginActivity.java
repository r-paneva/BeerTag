package com.beertag.android.views.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.beertag.android.R;
import com.beertag.android.models.User;
import com.beertag.android.repositories.base.BitmapCacheRepository;
import com.beertag.android.views.home.HomeActivity;
import com.stepstone.apprating.StringValue;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

import static com.beertag.android.utils.Constants.USER_EXTRA_KEY;
import static com.beertag.android.utils.Constants.USER_PROFILE_IMAGE_KEY;

public class LoginActivity extends DaggerAppCompatActivity implements LoginContracts.Navigator {
    SharedPreferences sharedPreferences;

    @Inject
    LoginPresenter mPresenter;

    @Inject
    LoginFragment mLoginFragment;

    @Inject
    BitmapCacheRepository mBitmapCacheRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mBitmapCacheRepository.clearBitmapCache();

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
        editor.putInt(String.valueOf(R.string.userId), user.getId());
        editor.putString(String.valueOf(R.string.username),user.getUserName());
        editor.putString(String.valueOf(R.string.fullname), user.getFirstName()+" "+user.getLastName());
        if(user.getImage()==null || user.getImage().length()>2) {
            editor.putString(String.valueOf(R.string.userpicture), user.getImage());
        }
        editor.apply();

        intent.putExtra(USER_EXTRA_KEY, user);
        startActivity(intent);
        finish();
    }
}
