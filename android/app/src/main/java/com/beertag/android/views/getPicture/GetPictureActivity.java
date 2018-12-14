package com.beertag.android.views.getPicture;

import android.content.Intent;
import android.os.Bundle;

import com.beertag.android.R;
import com.beertag.android.utils.Constants;
import com.beertag.android.views.BaseDrawerActivity;
import com.beertag.android.views.beersList.BeersListActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class GetPictureActivity extends BaseDrawerActivity {

    @Inject
    GetPictureFragment mView;

    @Inject
    public GetPictureActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_picture);

        ButterKnife.bind(this);

//        mFragment.setPresenter(mPresenter);
//        mFragment.setNavigator(this);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mView)
                .commit();
    }

    @Override
    protected long getIdentifier() {
        return Constants.GET_PICTURE_IDENTIFIER;
    }

    public void navigateToNextActivity() {
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

