package com.beertag.android.views.beersList;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.beertag.android.async.base.SchedulerProvider;
import com.beertag.android.models.Beer;
import com.beertag.android.services.base.BeersService;
import com.beertag.android.services.base.UsersService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

public class BeersListPresenter
        implements BeersListContracts.Presenter {

    private final BeersService mBeersService;
    private final UsersService mUsersService;
    private final SchedulerProvider mSchedulerProvider;
    private BeersListContracts.View mView;

    @Inject
    public BeersListPresenter(
            BeersService BeersService,
            UsersService usersService,
            SchedulerProvider schedulerProvider) {
        mBeersService = BeersService;
        mSchedulerProvider = schedulerProvider;
        mUsersService = usersService;
    }

    @Override
    public void loadBeers() {
        mView.showLoading();
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(BeersListActivity.getAppContext());

//        String username = sharedPrefs.getString("username", "");
        Disposable observable = Observable
                .create((ObservableOnSubscribe<List<Beer>>) emitter -> {
//                    List<Beer> beers = mBeersService.getBeersByUser(username);
                    List<Beer> beers = mBeersService.getAllBeers();
                    emitter.onNext(beers);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideLoading)
                .subscribe(
                        this::presentBeersToView,
                        error -> mView.showError(error)
                );
    }

    @Override
    public void filterBeers(String pattern) {
        mView.showLoading();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(BeersListActivity.getAppContext());
        String username = sharedPrefs.getString("username", "");
        Disposable observable = Observable
                .create((ObservableOnSubscribe<List<Beer>>) emitter -> {
//                    List<Beer> beers = mBeersService.getFilteredBeers(pattern, username);
                    List<Beer> beers = mBeersService.getAllBeers();
                    emitter.onNext(beers);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doOnError(error -> mView.showError(error))
                .doFinally(mView::hideLoading)
                .subscribe(this::presentBeersToView);
    }

    @Override
    public void selectBeer(Beer beer) {
        mView.showBeerDetails(beer);
    }

    private void presentBeersToView(List<Beer> Beers) {
        if (Beers.isEmpty()) {
            mView.showEmptyBeersList();
        } else {
            mView.showBeers(Beers);
        }
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void subscribe(BeersListContracts.View view) {
        mView = view;
    }

    @Override
    public UsersService getUsersService() {
        return mUsersService;
    }
}