package com.beertag.android.views.myBeers;

import com.beertag.android.models.Beer;
import com.beertag.android.services.base.UsersService;

import java.util.List;

public interface MyBeersListContracts {
    interface View {
        void setPresenter(Presenter presenter);

        void showBeers(List<Beer> beers);

        void showEmptyBeersList();

        void showError(Throwable e);

        void showLoading();

        void hideLoading();

        void showBeerDetails(Beer beer);

        void setUserId(int userId);
    }

    interface Presenter {
        void subscribe(View view);

        void loadBeers(int id);

        void filterBeers(String pattern);

        void selectBeer(Beer beer);

        void unsubscribe();
    }

    interface Navigator {
        void navigateWith(Beer beer);
    }
}