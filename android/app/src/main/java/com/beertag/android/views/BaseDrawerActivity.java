package com.beertag.android.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.beertag.android.repositories.base.BitmapCacheRepository;
import com.beertag.android.utils.Constants;
import com.beertag.android.R;
import com.beertag.android.utils.ImageEncoder;
import com.beertag.android.views.getPicture.GetPictureActivity;
import com.beertag.android.views.home.HomeActivity;
import com.beertag.android.views.login.LoginActivity;
import com.beertag.android.views.beerCreate.BeerCreateActivity;
import com.beertag.android.views.beerDetails.BeerDetailsActivity;
import com.beertag.android.views.beersList.BeersListActivity;
import com.beertag.android.views.userBeers.UserBeersListActivity;
import com.beertag.android.views.userBeers.UserBeersListPresenter;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import butterknife.BindView;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseDrawerActivity extends DaggerAppCompatActivity {

    private AccountHeader mHeader;
    private SharedPreferences mPreferences;

    @BindView(R.id.drawer_toolbar)
    Toolbar mToolbar;

    @Inject
    BitmapCacheRepository mBitmapCacheRepository;

    @Inject
    ImageEncoder mImageEncoder;

    @Inject
    UserBeersListPresenter mUserBeersListPresenter;

    public BaseDrawerActivity() {
        //empty constructor required
    }

    public void setupDrawer() {

        mHeader = new AccountHeaderBuilder()
                .withActivity(BaseDrawerActivity.this)

                .withTranslucentStatusBar(true)
                .withSelectionListEnabledForSingleProfile(false)
                .withCompactStyle(false)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(getUserDrawerName())
                                .withIcon(getUserProfileImage())
                                .withSelectable(true)
                                .withNameShown(true)
                )
                .build();


        PrimaryDrawerItem homeItem = new PrimaryDrawerItem()
                .withIdentifier(Constants.HOME_IDENTIFIER)
                .withName("Home");
        SecondaryDrawerItem myBeers = new SecondaryDrawerItem()
                .withIdentifier(Constants.MY_BEERS_IDENTIFIER)
                .withName("My Beers");


        PrimaryDrawerItem listBeersItem = new PrimaryDrawerItem()
                .withIdentifier(Constants.LIST_IDENTIFIER)
                .withName("List All Beers");

        PrimaryDrawerItem createBeerItem = new PrimaryDrawerItem()
                .withIdentifier(Constants.CREATE_IDENTIFIER)
                .withIcon(android.R.drawable.btn_plus)
                .withName("Create Beer");

        PrimaryDrawerItem imageItem = new PrimaryDrawerItem()
                .withIdentifier(Constants.GET_PICTURE_IDENTIFIER)
                .withIcon(android.R.drawable.btn_plus)
                .withName("Take a photo");


        PrimaryDrawerItem logoutItem = new PrimaryDrawerItem()
                .withIdentifier(Constants.LOGOUT_IDENTIFIER)
                .withIcon(android.R.drawable.button_onoff_indicator_off)
                .withName("Logout / Login");


        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)//getToolbar())
                .withAccountHeader(mHeader)
                .withCloseOnClick(true)
                .addDrawerItems(
                        homeItem.withIcon(R.drawable.ic_home_dark),
                        myBeers.withIcon(R.drawable.ic_cup_dark),
                        new DividerDrawerItem(),
                        listBeersItem.withIcon(R.drawable.ic_photo_library),
                        createBeerItem.withIcon(R.drawable.ic_cup_dark),
                        imageItem.withIcon(R.drawable.ic_camera),
                        new DividerDrawerItem(),
                        logoutItem.withIcon(R.drawable.ic_login)
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
        } else if (identifier == Constants.DETAILS_IDENTIFIER) {
            return new Intent(this, BeerDetailsActivity.class);
        } else if (identifier == Constants.GET_PICTURE_IDENTIFIER) {
            return new Intent(this, GetPictureActivity.class);
        } else if (identifier == Constants.MY_BEERS_IDENTIFIER) {
            return new Intent(this, UserBeersListActivity.class);
        } else if (identifier == Constants.LOGOUT_IDENTIFIER) {
            mBitmapCacheRepository.clearBitmapCache();
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.clear();
            editor.putString(String.valueOf(R.string.username), "");
            editor.apply();
            return new Intent(this, LoginActivity.class);
        } else if (identifier == Constants.HOME_IDENTIFIER) {
            return new Intent(this, HomeActivity.class);
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getSharedPreferences("com.beertag.android", Context.MODE_PRIVATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected String getUserDrawerName() {
        return mPreferences.getString(String.valueOf(R.string.fullname), "");
    }

    private Bitmap getUserProfileImage() {

        //initial check to handle the first try to get the user image at initial navigation to home it will not be loaded in lru cache
        Bitmap userImage = mBitmapCacheRepository.getBitmapFromCache(String.valueOf(Constants.USER_IMAGE_KEY));
        if (Objects.equals(userImage, null)) {
            String imageInString = mPreferences.getString(String.valueOf(R.string.userpicture), "");
            assert imageInString != null;
            if (Objects.equals(imageInString, null) || imageInString.equals("")) {
                return BitmapFactory.decodeResource(getResources(),
                        R.drawable.defaultuserpicture);
            } else {
                Bitmap userProfileImage = mImageEncoder.decodeStringToBitmap(imageInString);
                if (!Objects.equals(userProfileImage, null)) {
                    mBitmapCacheRepository.addBitmapToBitmapCache(userProfileImage, String.valueOf(Constants.USER_IMAGE_KEY));
                    return mBitmapCacheRepository.getBitmapFromCache(String.valueOf(Constants.USER_IMAGE_KEY));
                } else {
                    BitmapFactory.decodeResource(getResources(),
                            R.drawable.defaultuserpicture);
                }
            }

            return BitmapFactory.decodeResource(getResources(),
                    R.drawable.defaultuserpicture);
        } else {
            return userImage;
        }
    }
}