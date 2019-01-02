package com.beertag.android.views.beerDetails;

import android.graphics.Bitmap;

import com.beertag.android.async.base.SchedulerProvider;
import com.beertag.android.models.Beer;
import com.beertag.android.models.Drink;
import com.beertag.android.models.UserBeers;
import com.beertag.android.models.UserBeersIdentity;
import com.beertag.android.models.User;
import com.beertag.android.repositories.base.BitmapCacheRepository;
import com.beertag.android.services.base.BeersService;
import com.beertag.android.services.base.UserBeersService;
import com.beertag.android.services.base.UsersService;
import com.beertag.android.utils.Constants;
import com.beertag.android.utils.ImageEncoder;
import com.beertag.android.views.home.HomeActivity;

import java.util.Objects;
import java.util.function.Consumer;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

public class BeerDetailsPresenter  implements BeerDetailsContracts.Presenter {

    private final BeersService mBeersService;
    private final UserBeersService mUserBeersService;
    private final UsersService mUserService;
    private final SchedulerProvider mSchedulerProvider;
    private final ImageEncoder mImageEncoder;
    private final BitmapCacheRepository mBitmapCacheRepository;
    private BeerDetailsContracts.View mView;

    private int mBeerId;

    @Inject
    public BeerDetailsPresenter(
            BeersService beersService,
            UserBeersService userBeersService,
            UsersService userService,
            SchedulerProvider schedulerProvider,
            ImageEncoder imageEncoder,
            BitmapCacheRepository bitmapCacheRepository) {
        mBeersService = beersService;
        mUserService = userService;
        mSchedulerProvider = schedulerProvider;
        mUserBeersService = userBeersService;
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
    public void setUserId(int userId) {
        mUserId = userId;
    }

    @Override
    public void selectPictureFromGalleryButtonClickIsClicked() {
        mView.showOptionToChooseImage();
    }

    @Override
    public void takePictureButtonIsClicked() {
        mView.presentOptionToTakePicture();
    }

    private int mUserId = HomeActivity.getVariable();  // Accessing in Another class

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
                .subscribe(b->{
                    mView.showBeer(b);
                    mView.setBeer(b);
                });

    }

    @Override
    public int loadUserId() {
        return mUserId;
    }

    @Override
    public int loadBeerId() {
        return mBeerId;
    }

    @Override
    public void loadUser(){

        Disposable observable = Observable
                .create((ObservableOnSubscribe<User>) emitter -> {
                    User user = mUserService.getById(mUserId);
                    emitter.onNext(user);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideLoading)
                .doOnError(mView::showError)
                .subscribe(mView::setUser);
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


    @Override
    public void setRating(int beerId, int userId, Drink drink, Beer beer, int stars) {
        UserBeersIdentity userBeersIdentity = new UserBeersIdentity(beerId, mUserId);
        UserBeers userbeer = new UserBeers(userBeersIdentity, stars, drink, beer );
        Disposable disposable = (Disposable) Observable
                .create((ObservableOnSubscribe<UserBeers>) emitter -> {
                    mUserBeersService.createUserBeer(userbeer);
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doOnEach(x -> mView.hideLoading())
                .doOnError(mView::showError)
                .subscribe(s -> {
                    loadBeer();
                });
    }

    @Override
    public void loadUserBeers(int beerId, int userId){
        Disposable observable = Observable
                .create((ObservableOnSubscribe<UserBeers>) emitter -> {
                    UserBeers userbeers = mUserBeersService.getById(beerId, userId);
                    emitter.onNext(userbeers);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideLoading)
                .doOnError(mView::showError)
                .subscribe(r -> {
                    System.out.println("**************************************************************************************************");
                    System.out.println(r.getDrink());
                    System.out.println("**************************************************************************************************");
                    Consumer<UserBeers> setUserBeers = mView::setUserBeers;
                });

    }


}