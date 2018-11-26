package com.beertag.android.views.beerCreate;


import com.beertag.android.Constants;
import com.beertag.android.async.base.SchedulerProvider;
import com.beertag.android.models.Beer;
import com.beertag.android.models.Country;
import com.beertag.android.services.base.BeersService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

public class BeerCreatePresenter implements BeerCreateContracts.Presenter {

    private final BeersService mBeersService;
    private final SchedulerProvider mSchedulerProvider;
    private BeerCreateContracts.View mView;

    @Inject
    public BeerCreatePresenter(
            BeersService beerService,
            SchedulerProvider schedulerProvider) {
        mBeersService = beerService;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void subscribe(BeerCreateContracts.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void save(Beer beer) {
        if (beer.getName().length() < Constants.BEER_NAME_MIN_VALUE ||
                beer.getName().length() > Constants.BEER_NAME_MAX_VALUE) {
            mView.showMessage(Constants.NAME_ERROR_MESSAGE);
        } else {
            mView.showLoading();
            Disposable disposable = Observable
                .create((ObservableOnSubscribe<Beer>) emitter -> {
                    mBeersService.createBeer(beer);
                    emitter.onNext(beer);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doOnEach(x -> mView.hideLoading())
                .doOnError(error -> {if (error instanceof NullPointerException) {
                            mView.showMessage(Constants.UNSUCCESSFUL_CREATION);
                        } else {
                            mView.showError(error);
                        }
                    })
                .subscribe(s -> mView.navigateToHome());
        }
    }

    private void accept(Notification<Beer> x) {
        mView.hideLoading();
    }

    private void accept(Beer s) {
        mView.navigateToHome();
    }

    private void presentCountriesToView(List<Country> countries) {
        if (countries.isEmpty()) {
            mView.showEmptyCountryList();
        } else {
            mView.showCountries(countries);
        }
    }

//private int getResponceCode(){
//        int statusCode = 0;
//        try {
//            url = new URL(Constants.BASE_SERVER_URL+ "/beers");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            statusCode = http.getResponseCode();
//        } catch (java.io.IOException e) {
//            e.printStackTrace();
//        }
//      return statusCode;
//    }

}
