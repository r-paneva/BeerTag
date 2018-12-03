package com.beertag.android.views.Login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.beertag.android.R;
import com.beertag.android.models.User;
import com.beertag.android.views.beersList.BeersListActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContracts.Navigator {
    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.bt_login)
    Button mButtonLogin;

    @Inject
    LoginPresenter mLoginPresenter;
    Activity mActivity;
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mActivity = this;
        ButterKnife.bind(this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    mUser = mLoginPresenter.getUserByName(String.valueOf(mUsername.getText()));
                    if (mUser != null) {
                        editor.putString("username", String.valueOf(mUsername.getText()));
                    } else {
                        Toast.makeText(mActivity, "No such username in the database!", Toast.LENGTH_LONG).show();
                    }
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), BeersListActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
