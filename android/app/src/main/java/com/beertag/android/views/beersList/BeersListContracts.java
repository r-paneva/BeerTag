package com.beertag.android.views.beersList;

import com.beertag.android.models.Beer;
import com.beertag.android.services.base.UsersService;

import java.util.List;

public interface BeersListContracts {
    interface View {
        void setPresenter(Presenter presenter);

        void showBeers(List<Beer> beers);

        void showEmptyBeersList();

        void showError(Throwable e);

        void showLoading();

        void hideLoading();

        void showBeerDetails(Beer beer);
    }

    interface Presenter {
        void subscribe(View view);

        void loadBeers();

        void filterBeers(String pattern);

        void selectBeer(Beer beer);

        UsersService getUsersService();
    }

    interface Navigator {
        void navigateWith(Beer beer);
    }
}