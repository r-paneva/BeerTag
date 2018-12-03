package com.beertag.android.views.beerDetails;

import com.beertag.android.async.base.SchedulerProvider;
import com.beertag.android.models.Beer;
import com.beertag.android.models.RatingVote;
import com.beertag.android.models.User;
import com.beertag.android.services.base.BeersService;
import com.beertag.android.services.base.RatingVoteService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

public class BeerDetailsPresenter
        implements BeerDetailsContracts.Presenter {
    private final BeersService mBeersService;
    private final RatingVoteService mRatingVoteService;
    private final SchedulerProvider mSchedulerProvider;

    private BeerDetailsContracts.View mView;
    private int mBeerId;

    @Inject
    public BeerDetailsPresenter(
            BeersService beersService,
            RatingVoteService ratingVoteService,
            SchedulerProvider schedulerProvider) {
        mBeersService = beersService;
        mSchedulerProvider = schedulerProvider;
        mRatingVoteService = ratingVoteService;
    }


    @Override
    public void subscribe(BeerDetailsContracts.View view) {
        mView = view;
    }

    @Override
    public void loadBeer() {
        mView.showLoading();
        Disposable observable = Observable
                .create((ObservableOnSubscribe<Beer>) emitter -> {
                    Beer beer = mBeersService.getDetailsById(mBeerId);
                    emitter.onNext(beer);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doOnError(mView::showError)
                .subscribe(mView::showBeer);
    }

    @Override
    public void setBeerId(Integer beerId) {
        mBeerId = beerId;
    }

    @Override
    public void setRating(User whoRates, Beer rated, int stars) {
        RatingVote ratingVote = new RatingVote(stars, whoRates.getUserName(), rated.getName());
        Disposable disposable = (Disposable) Observable
                .create((ObservableOnSubscribe<RatingVote>) emitter -> {
                    mRatingVoteService.createRatingVote(ratingVote);
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doOnEach(x -> mView.hideLoading())
                .doOnError(mView::showError)
                .subscribe(s -> loadBeer());
    }

}