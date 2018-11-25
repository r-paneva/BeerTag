package com.beertag.android.views.beerCreate;


import com.beertag.android.models.Beer;

import java.io.IOException;

public interface BeerCreateContracts {
    interface View {

        void setPresenter(Presenter presenter);

        void navigateToHome();

        void showError(Throwable throwable);

        void hideLoading();

        void showLoading();

        void showMessage(String message);
    }

    interface Presenter {

        void subscribe(View view);

        void unsubscribe();

        void save(Beer beer) throws IOException;
    }

    public interface Navigator {

        void navigateToHome();
    }
}
