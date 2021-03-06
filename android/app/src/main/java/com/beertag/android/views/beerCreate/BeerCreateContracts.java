package com.beertag.android.views.beerCreate;


import android.graphics.Bitmap;

import com.beertag.android.models.Beer;
import com.beertag.android.models.Country;
import com.beertag.android.models.Style;
import com.beertag.android.models.Tag;

import java.io.IOException;
import java.util.List;

public interface BeerCreateContracts {
    interface View {

        void setPresenter(Presenter presenter);

        void navigateToHome();

        void showError(Throwable throwable);

        void hideLoading();

        void showLoading();

        void showMessage(String message);

        void showCountries(List<Country> countries);

        void showEmptyList();

        void showStyles(List<Style> styles);

        void showTags(List<Tag> tags);

        void setCountry(Country country);

        void setStyle(Style style);

        void setTag (Tag tag);

        void presentOptionToTakePicture();
    }

    interface Presenter {

        void subscribe(View view);

        void unsubscribe();

        void save(Beer beer) throws IOException;

        void loadCountries();

        void loadStyles();

        void loadTags();

        void loadCountry(String name);

        void loadStyle(String name);

        void loadTag(String name);

        void takePictureButtonIsClicked();

    }

     interface Navigator {
        void navigateToHome();
    }
}
