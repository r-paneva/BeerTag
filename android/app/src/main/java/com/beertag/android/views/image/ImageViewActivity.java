package com.beertag.android.views.image;

import android.content.Intent;
import android.os.Bundle;

import com.beertag.android.R;
import com.beertag.android.utils.Constants;
import com.beertag.android.views.BaseDrawerActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class ImageViewActivity extends BaseDrawerActivity {

    @Inject
    ImageViewContracts.Presenter mImageViewPresenter;

    @Inject
    ImageViewFragment mImageViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        String imageString = intent.getStringExtra(Constants.IMAGE_MESSAGE_EXTRA);

        mImageViewPresenter.setImage(imageString);

        mImageViewFragment.setPresenter(mImageViewPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fr_image_view, mImageViewFragment)
                .commit();
    }

    @Override
    protected long getIdentifier() {
        return Constants.IMAGE_IDENTIFIER;
    }
}
