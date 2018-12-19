package com.beertag.android.views.myBeers;

import com.beertag.android.async.base.SchedulerProvider;
import com.beertag.android.models.Beer;
import com.beertag.android.models.MyBeers;
import com.beertag.android.services.base.BeersService;
import com.beertag.android.services.base.RatingVoteService;
import com.beertag.android.services.base.UsersService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

public class MyBeersListPresenter
        implements MyBeersListContracts.Presenter {

    private final BeersService mBeersService;
    private final UsersService mUsersService;
    private final RatingVoteService mRatingService;
    private final SchedulerProvider mSchedulerProvider;
    private MyBeersListContracts.View mView;


    @Inject
    public MyBeersListPresenter(
            BeersService BeersService,
            UsersService usersService,
            RatingVoteService ratingService,
            SchedulerProvider schedulerProvider) {
        mBeersService = BeersService;
        mRatingService = ratingService;
        mSchedulerProvider = schedulerProvider;
        mUsersService = usersService;
    }

    @Override
    public void loadMyBeers(int userId) {
        mView.showLoading();
        List<Beer> beers = new ArrayList<>();
        Disposable observable = Observable
                .create((ObservableOnSubscribe<List<MyBeers>>) emitter -> {
                    List<MyBeers> myBeers = mRatingService.getBeersByUserId(userId);
                    emitter.onNext(myBeers);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideLoading)
                .subscribe( myBeers -> {
                            for (MyBeers mb : myBeers) {
                                beers.add(mb.getBeer());
                            }
                            presentMyBeersToView(myBeers);
                        },
                        error -> mView.showError(error)
                );
    }

    @Override
    public void selectBeer(MyBeers myBeers) {
        mView.showBeerDetails(myBeers);
    }

    private void presentMyBeersToView(List<MyBeers> myBeers){
        if (myBeers.isEmpty()) {
            mView.showEmptyBeersList();
        } else {
            mView.showMyBeers(myBeers);
        }
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void subscribe(MyBeersListContracts.View view) {
        mView = view;
    }

}