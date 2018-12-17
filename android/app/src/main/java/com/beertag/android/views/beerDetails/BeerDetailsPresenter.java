package com.beertag.android.views.beerDetails;

import android.graphics.Bitmap;

import com.beertag.android.async.base.SchedulerProvider;
import com.beertag.android.models.Beer;
import com.beertag.android.models.User;
import com.beertag.android.repositories.base.BitmapCacheRepository;
import com.beertag.android.services.base.BeersService;
import com.beertag.android.services.base.RatingVoteService;
import com.beertag.android.utils.Constants;
import com.beertag.android.utils.ImageEncoder;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

public class BeerDetailsPresenter  implements BeerDetailsContracts.Presenter {

    private final BeersService mBeersService;
    private final RatingVoteService mRatingVoteService;
    private final SchedulerProvider mSchedulerProvider;
    private final ImageEncoder mImageEncoder;
    private final BitmapCacheRepository mBitmapCacheRepository;
    private BeerDetailsContracts.View mView;

    private int mBeerId;

    @Inject
    public BeerDetailsPresenter(
            BeersService beersService,
            RatingVoteService ratingVoteService,
            SchedulerProvider schedulerProvider,
            ImageEncoder imageEncoder,
            BitmapCacheRepository bitmapCacheRepository) {
        mBeersService = beersService;
        mSchedulerProvider = schedulerProvider;
        mRatingVoteService = ratingVoteService;
        mImageEncoder = imageEncoder;
        mBitmapCacheRepository = bitmapCacheRepository;
    }


    @Override
    public void subscribe(BeerDetailsContracts.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void setBeerId(int beerId) {
        mBeerId = beerId;
    }

    @Override
    public void selectPictureFromGalleryButtonClickIsClicked() {
        mView.showOptionToChooseImage();
    }

    @Override
    public void takePictureButtonIsClicked() {
        mView.presentOptionToTakePicture();
    }

    @Override
    public void loadBeer() {
        Disposable observable = Observable
                .create((ObservableOnSubscribe<Beer>) emitter -> {
                    Beer beer = mBeersService.getDetailsById(mBeerId);
                    emitter.onNext(beer);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideLoading)
                .doOnError(mView::showError)
                .subscribe(mView::showBeer);
    }

    @Override
    public void setRating(User whoRates, Beer rated, int stars) {
//        MyBeers ratingVote = new MyBeers(stars, whoRates.getUserName(), rated.getName());
//        Disposable disposable = (Disposable) Observable
//                .create((ObservableOnSubscribe<MyBeers>) emitter -> {
//                    mRatingVoteService.createRatingVote(ratingVote);
//                })
//                .subscribeOn(mSchedulerProvider.background())
//                .observeOn(mSchedulerProvider.ui())
//                .doOnEach(x -> mView.hideLoading())
//                .doOnError(mView::showError)
//                .subscribe(s -> loadBeer());
    }

    @Override
    public void updateBeer(Beer updatedBeer) {
        mView.showLoading();
        Disposable observable = Observable
                .create((ObservableOnSubscribe<Beer>) emitter -> {
                    Beer beer = mBeersService.updateBeer(updatedBeer);
                    emitter.onNext(beer);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(mView::hideLoading, error -> mView.showError(error));
    }


    @Override
    public void newImageIsChosen(Bitmap image) {
        String imageString = mImageEncoder.encodeBitmapToString(image);

        if (Objects.equals(imageString, null)) {
            mView.showMessage(Constants.IMAGE_CHANGE_ERROR_MESSAGE);

        } else {
            Disposable observable = Observable
                    .create((ObservableOnSubscribe<Beer>) emitter -> {
                        Beer beer = mBeersService.getDetailsById(mBeerId);
                        emitter.onNext(beer);
                        emitter.onComplete();
                    })
                    .subscribeOn(mSchedulerProvider.background())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(beerResult -> updateBeerPicture(beerResult, imageString),
                            error -> mView.showError(error));

        }

    }

    @Override
    public void updateBeerPicture(Beer beer, String imageString) {
        if (!Objects.equals(beer, null)) {
            beer.setImage(imageString);
            Disposable observable = Observable
                    .create((ObservableOnSubscribe<Beer>) emitter -> {
                        mBeersService.updateBeer(beer);
                        emitter.onNext(beer);
                        emitter.onComplete();
                    })
                    .subscribeOn(mSchedulerProvider.background())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(this::decodeImageAndPresentToView,
                            error -> mView.showError(error));
        } else {
            mView.showMessage(Constants.IMAGE_CHANGE_ERROR_MESSAGE);
        }
    }

    @Override
    public void decodeImageAndPresentToView(Beer beer) {
        String beerPicture = beer.getImage();
        Bitmap decodedBeerPicture = mImageEncoder.decodeStringToBitmap(beerPicture);
        if (Objects.equals(decodedBeerPicture, null)) {
            mView.showMessage(Constants.IMAGE_CHANGE_ERROR_MESSAGE);
        } else {
            mView.showBeerImage(decodedBeerPicture);
            mBitmapCacheRepository.addBitmapToBitmapCache(decodedBeerPicture, Constants.BEER_IMAGE_KEY);
        }
    }
}