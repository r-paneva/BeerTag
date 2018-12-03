package com.beertag.android.views.beerDetails;


import com.beertag.android.models.Beer;
import com.beertag.android.models.User;

public interface BeerDetailsContracts {
    interface View {
        void showBeer(Beer beer);

        void setPresenter(Presenter presenter);

        void showError(Throwable e);

        void showLoading();

        void hideLoading();
    }

    interface Presenter {
        void subscribe(View view);

        void loadBeer();

        void setBeerId(Integer beerId);

        void setRating(User whoRates, Beer rated, int stars);
    }
}