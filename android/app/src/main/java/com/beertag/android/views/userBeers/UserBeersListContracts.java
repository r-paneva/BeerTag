package com.beertag.android.views.userBeers;

import com.beertag.android.models.UserBeers;

import java.util.List;

public interface UserBeersListContracts {
    interface View {
        void setPresenter(Presenter presenter);

        void showUserBeers(List<UserBeers> userBeers);

        void showEmptyBeersList();

        void showError(Throwable e);

        void showLoading();

        void hideLoading();

        void showBeerDetails(UserBeers userbeer);

        void setUserId(int userId);
    }

    interface Presenter {
        void subscribe(View view);

        void loadUserBeers(int id);

        void selectBeer(UserBeers userbeer);

        void unsubscribe();
    }

    interface Navigator {
        void navigateWith(UserBeers userBeer);
    }
}