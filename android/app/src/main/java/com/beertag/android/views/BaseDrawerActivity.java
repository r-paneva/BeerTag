package com.beertag.android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import com.beertag.android.Constants;
import com.beertag.android.R;
import com.beertag.android.views.Login.LoginActivity;
import com.beertag.android.views.beerCreate.BeerCreateActivity;
import com.beertag.android.views.beersList.BeersListActivity;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import butterknife.BindView;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseDrawerActivity extends DaggerAppCompatActivity {

    @BindView(R.id.drawer_toolbar)
    Toolbar mToolbar;

    public BaseDrawerActivity() {

    }

    public void setupDrawer() {
        PrimaryDrawerItem listBeersItem = new PrimaryDrawerItem()
                .withIdentifier(Constants.LIST_IDENTIFIER)
                .withIcon(android.R.drawable.ic_input_get)
                .withName("List All Beers");

        PrimaryDrawerItem createBeerItem = new PrimaryDrawerItem()
                .withIdentifier(Constants.CREATE_IDENTIFIER)
                .withIcon(android.R.drawable.btn_plus)
                .withName("Create Beer");

        PrimaryDrawerItem logoutItem = new PrimaryDrawerItem()
                .withIdentifier(Constants.LOGOUT_IDENTIFIER)
                .withIcon(android.R.drawable.button_onoff_indicator_off)
                .withName("Logout / Login");

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        listBeersItem,
//                        new DividerDrawerItem(),
                        createBeerItem,
                        new DividerDrawerItem(),
                        logoutItem
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) ->
                {
                    long identifier = drawerItem.getIdentifier();

                    if (getIdentifier() == identifier) {
                        return false;
                    }

                    Intent intent = getNextIntent(identifier);
                    if (intent == null) {
                        return false;
                    }

                    startActivity(intent);
                    return true;
                })
                .build();
    }

    private Intent getNextIntent(long identifier) {
        if (identifier == Constants.CREATE_IDENTIFIER) {
            return new Intent(this, BeerCreateActivity.class);
        } else if (identifier == Constants.LIST_IDENTIFIER) {
            return new Intent(this, BeersListActivity.class);
        } else if (identifier == Constants.LOGOUT_IDENTIFIER) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putString("username", "");
            editor.commit();
            //return new Intent(this, BeersListActivity.class);
            return new Intent(this, LoginActivity.class);
        }

        return null;
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected abstract long getIdentifier();

    @Override
    protected void onStart() {
        super.onStart();
        setupDrawer();
    }
}