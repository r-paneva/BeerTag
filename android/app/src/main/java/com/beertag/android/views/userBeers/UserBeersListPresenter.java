package com.beertag.android.views.userBeers;

import com.beertag.android.async.base.SchedulerProvider;
import com.beertag.android.models.Beer;
import com.beertag.android.models.UserBeers;
import com.beertag.android.services.base.BeersService;
import com.beertag.android.services.base.UserBeersService;
import com.beertag.android.services.base.UsersService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

public class UserBeersListPresenter
        implements UserBeersListContracts.Presenter {

    private final BeersService mBeersService;
    private final UsersService mUsersService;
    private final UserBeersService mRatingService;
    private final SchedulerProvider mSchedulerProvider;
    private UserBeersListContracts.View mView;


    @Inject
    public UserBeersListPresenter(
            BeersService BeersService,
            UsersService usersService,
            UserBeersService ratingService,
            SchedulerProvider schedulerProvider) {
        mBeersService = BeersService;
        mRatingService = ratingService;
        mSchedulerProvider = schedulerProvider;
        mUsersService = usersService;
    }

    @Override
    public void loadUserBeers(int userId) {
        mView.showLoading();
        List<Beer> beers = new ArrayList<>();
        Disposable observable = Observable
                .create((ObservableOnSubscribe<List<UserBeers>>) emitter -> {
                    List<UserBeers> userBeers = mRatingService.getBeersByUserId(userId);
                    emitter.onNext(userBeers);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideLoading)
                .subscribe( userBeers -> {
                            for (UserBeers mb : userBeers) {
                                beers.add(mb.getBeer());
                            }
                            presentUserBeersToView(userBeers);
                        },
                        error -> mView.showError(error)
                );
    }

    @Override
    public void selectBeer(UserBeers userBeers) {
        mView.showBeerDetails(userBeers);
    }

    private void presentUserBeersToView(List<UserBeers> userBeers){
        if (userBeers.isEmpty()) {
            mView.showEmptyBeersList();
        } else {
            mView.showUserBeers(userBeers);
        }
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void subscribe(UserBeersListContracts.View view) {
        mView = view;
    }

}