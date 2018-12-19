package com.beertag.android.views.myBeers;

import com.beertag.android.models.MyBeers;

import java.util.List;

public interface MyBeersListContracts {
    interface View {
        void setPresenter(Presenter presenter);

        void showMyBeers(List<MyBeers> myBeers);

        void showEmptyBeersList();

        void showError(Throwable e);

        void showLoading();

        void hideLoading();

        void showBeerDetails(MyBeers mybeer);

        void setUserId(int userId);
    }

    interface Presenter {
        void subscribe(View view);

        void loadMyBeers(int id);

        void selectBeer(MyBeers mybeer);

        void unsubscribe();
    }

    interface Navigator {
        void navigateWith(MyBeers myBeer);
    }
}