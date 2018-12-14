package com.beertag.android.views.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.beertag.android.R;
import com.beertag.android.models.User;
import com.beertag.android.utils.Constants;
import com.beertag.android.views.BaseDrawerActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.beertag.android.utils.Constants.PREFERENCES_USER_FULL_NAME_KEY;
import static com.beertag.android.utils.Constants.PREFERENCES_USER_ID_KEY;
import static com.beertag.android.utils.Constants.PREFERENCES_USER_NAME_KEY;
import static com.beertag.android.utils.Constants.USER_EXTRA;

public class HomeActivity extends BaseDrawerActivity {

    @Inject
    HomeFragment mHomeFragment;

    @Inject
    HomeContracts.Presenter mPresenter;

    private String mName="";
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mContext = getApplicationContext();
        Intent incomingIntent = getIntent();

        if (incomingIntent.hasExtra(USER_EXTRA)) {
            User user = (User) incomingIntent.getSerializableExtra(USER_EXTRA);
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

    public static Context getAppContext() {
        return mContext;
    }

    private void persistUserSessionData() {
        SharedPreferences result = getSharedPreferences("com.beertag.android", Context.MODE_PRIVATE);
        mName = result.getString(String.valueOf(R.string.username), "");
    }
}
