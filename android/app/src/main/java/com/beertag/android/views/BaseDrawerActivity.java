package com.beertag.android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import butterknife.BindView;
import dagger.android.support.DaggerAppCompatActivity;

import static com.beertag.android.utils.Constants.PREFERENCES_USER_ID_KEY;
import static com.beertag.android.utils.Constants.PREFERENCES_USER_NAME_KEY;
import static com.beertag.android.utils.Constants.USER_PROFILE_IMAGE_KEY;

public abstract class BaseDrawerActivity extends DaggerAppCompatActivity {

    private Drawer mDrawer;
    private AccountHeader mHeader;
    private SharedPreferences mPreferences;

    @BindView(R.id.drawer_toolbar)
    Toolbar mToolbar;

    @Inject
    BitmapCacheRepository mBitmapCacheRepository;

    @Inject
    ImageEncoder mImageEncoder;

    public BaseDrawerActivity() {
        //empty constructor required
    }

    public void setupDrawer() {

        mHeader = new AccountHeaderBuilder()
                .withActivity(BaseDrawerActivity.this)
//                    .withHeaderBackground(R.drawable.drawerheader)
                .withTranslucentStatusBar(true)
                .withSelectionListEnabledForSingleProfile(false)
                .withCompactStyle(false)
                .addProfiles(
                        new ProfileDrawerItem()
//                                .withName(getUserDrawerName())
                                .withIcon(getUserProfileImage())
                                .withSelectable(true)
                                .withNameShown(true)
                )
                .build();


        PrimaryDrawerItem homeItem = new PrimaryDrawerItem()
                .withIdentifier(Constants.HOME_IDENTIFIER)
                .withName("Home");
//            SecondaryDrawerItem myDrunkBeers = new SecondaryDrawerItem()
//                    .withIdentifier(Constants.DRUNK_BEERS)
//                    .withName("Beers that i drank");
//            SecondaryDrawerItem myWillDrinkBeers = new SecondaryDrawerItem()
//                    .withIdentifier(Constants.DRUNK_BEERS)
//                    .withName("Beers that i drank");


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
        } else if (identifier == Constants.LOGOUT_IDENTIFIER) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putString("username", "");
            editor.commit();
            return new Intent(this, LoginActivity.class);
        } else if ( identifier == Constants.HOME_IDENTIFIER ) {
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
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
//        mDrawer.closeDrawer();
    }

    protected String getUserName() {
        return mPreferences.getString(String.valueOf(R.string.username), "");
    }

    private Bitmap getUserProfileImage() {

        //initial check to handle the first try to get the user image at initial navigation to home it will not be loaded in lru cache
        Bitmap userImage =  mBitmapCacheRepository.getBitmapFromCache(USER_PROFILE_IMAGE_KEY);

        if (Objects.equals(userImage, null)) {

            String imageInString = mPreferences.getString(USER_PROFILE_IMAGE_KEY, "");
            if (Objects.equals(imageInString, null) || imageInString.equals("")) {
                return BitmapFactory.decodeResource(getResources(),
                        R.drawable.defaultuserpicture);
            } else {
                Bitmap userProfileImage = mImageEncoder.decodeStringToBitmap(imageInString);
                if (!Objects.equals(userProfileImage, null)) {
                    mBitmapCacheRepository.addBitmapToBitmapCache(userProfileImage, USER_PROFILE_IMAGE_KEY);
                    return mBitmapCacheRepository.getBitmapFromCache(USER_PROFILE_IMAGE_KEY);
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
