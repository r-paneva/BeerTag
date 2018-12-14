package com.beertag.android.views.beerCreate;

import android.content.Intent;
import android.os.Bundle;

import com.beertag.android.utils.Constants;
import com.beertag.android.R;
import com.beertag.android.views.BaseDrawerActivity;
import com.beertag.android.views.image.ImageViewActivity;
import com.beertag.android.views.beersList.BeersListActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class BeerCreateActivity extends BaseDrawerActivity implements BeerCreateContracts.Navigator {

    @Inject
    BeerCreateFragment mView;

    @Inject
    BeerCreateContracts.Presenter mPresenter;

    @Inject
    public BeerCreateActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_create);
        ButterKnife.bind(this);

        mView.setPresenter(mPresenter);
        mView.setNavigator(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mView)
                .commit();
    }

    @Override
    protected long getIdentifier() {
        return Constants.CREATE_IDENTIFIER;
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, BeersListActivity.class);
        startActivity(intent);
        finish();
    }

//    @Override
//    public void navigateToImageView(String beerImage) {
//        Intent intent = new Intent(this, ImageViewActivity.class);
//        intent.putExtra(Constants.IMAGE_MESSAGE_EXTRA, beerImage);
//        startActivity(intent);
//    }
}
