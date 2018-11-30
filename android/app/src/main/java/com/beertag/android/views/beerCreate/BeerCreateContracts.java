package com.beertag.android.views.beerCreate;


import com.beertag.android.models.Beer;
import com.beertag.android.models.Country;

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

        void showEmptyCountryList();
    }

    interface Presenter {

        void subscribe(View view);

        void unsubscribe();

        void save(Beer beer) throws IOException;

        void loadCountries();
    }

    public interface Navigator {

        void navigateToHome();
    }
}
