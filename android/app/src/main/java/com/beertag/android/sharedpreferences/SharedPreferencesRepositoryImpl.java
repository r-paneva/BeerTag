package com.beertag.android.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.beertag.android.sharedpreferences.base.SharedPreferencesRepository;


public class SharedPreferencesRepositoryImpl  implements SharedPreferencesRepository {

    private SharedPreferences prefs;

    public SharedPreferencesRepositoryImpl(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

//    @Override
//    public boolean isLoggedIn() {
//
//        return prefs.getBoolean(Constants.IS_LOGGED_IN, false);
//    }
    @Override
    public String whoIsLoggedIn() {
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String username= prefs.getString("username", "");
        return prefs.getString("username", "");
    }
}
