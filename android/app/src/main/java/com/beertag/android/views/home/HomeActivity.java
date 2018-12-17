package com.beertag.android.views.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.beertag.android.R;
import com.beertag.android.models.User;
import com.beertag.android.utils.Constants;
import com.beertag.android.views.BaseDrawerActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.beertag.android.utils.Constants.USER_EXTRA_KEY;

public class HomeActivity extends BaseDrawerActivity {

    @Inject
    HomeFragment mHomeFragment;

    @Inject
    HomeContracts.Presenter mPresenter;

    private String mName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Intent incomingIntent = getIntent();

        if (incomingIntent.hasExtra(USER_EXTRA_KEY)) {
            User user = (User) incomingIntent.getSerializableExtra(USER_EXTRA_KEY);
        }
        persistUserSessionData();
        mPresenter.setUserName(mName);
        mHomeFragment.setPresenter(mPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fr_home, mHomeFragment)
                .commit();
    }

    @Override
    protected long getIdentifier() {
        return Constants.HOME_IDENTIFIER;
    }


    private void persistUserSessionData() {
        SharedPreferences result = getSharedPreferences("com.beertag.android", Context.MODE_PRIVATE);
        mName = result.getString(String.valueOf(R.string.username), "");
    }
}
