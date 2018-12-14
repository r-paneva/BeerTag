package com.beertag.android.views.beerCreate;


import android.graphics.Bitmap;

import com.beertag.android.async.base.SchedulerProvider;
import com.beertag.android.models.Beer;
import com.beertag.android.models.Country;
import com.beertag.android.models.Style;
import com.beertag.android.models.Tag;
import com.beertag.android.services.base.BeersService;
import com.beertag.android.services.base.CountryService;
import com.beertag.android.services.base.StyleService;
import com.beertag.android.services.base.TagService;
import com.beertag.android.utils.Constants;
import com.beertag.android.utils.ImageEncoder;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

public class BeerCreatePresenter implements BeerCreateContracts.Presenter {

    private final BeersService mBeersService;
    private final SchedulerProvider mSchedulerProvider;
    private BeerCreateContracts.View mView;
    private final CountryService mCountryService;
    private final StyleService mStyleService;
    private final TagService mTagService;

    @Inject
    BeerCreatePresenter(
            BeersService beerService,
            SchedulerProvider schedulerProvider,
            CountryService countriesService,
            StyleService styleService,
            TagService tagService) {
        mBeersService = beerService;
        mSchedulerProvider = schedulerProvider;
        mCountryService = countriesService;
        mStyleService = styleService;
        mTagService = tagService;
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
        } else if (beer.getCountry().getName().isEmpty()) {
            mView.showMessage(Constants.COUNTRY_ERROR_MESSAGE);
        } else if (beer.getStyle().getName().isEmpty()) {
            mView.showMessage(Constants.STYLE_ERROR_MESSAGE);
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
                    .doOnError(error -> {
                        if (error instanceof NullPointerException) {
                            mView.showMessage(Constants.UNSUCCESSFUL_CREATION);
                        } else {
                            mView.showError(error);
                        }
                    })
                    .subscribe(s -> mView.navigateToHome());
        }
    }

    @Override
    public void loadCountries() {
        Disposable observable = Observable
                .create((ObservableOnSubscribe<List<Country>>) emitter -> {
                    List<Country> countries = mCountryService.getAllCountries();
                    emitter.onNext(countries);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        this::presentCountriesToView,
                        error -> mView.showError(error)
                );
    }

    @Override
    public void loadStyles() {
        Disposable observable = Observable
                .create((ObservableOnSubscribe<List<Style>>) emitter -> {
                    List<Style> styles = mStyleService.getAllStyles();
                    emitter.onNext(styles);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        this::presentStylesToView,
                        error -> mView.showError(error)
                );
    }

    @Override
    public void loadCountry(String name) {

        Disposable observable = Observable
                .create((ObservableOnSubscribe<Country>) emitter -> {
                    Country country = mCountryService.getByName(name);
                    emitter.onNext(country);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(mView::setCountry,
                        error -> mView.showError(error)
                );
    }

    @Override
    public void loadStyle(String name) {
        Disposable observable = Observable
                .create((ObservableOnSubscribe<Style>) emitter -> {
                    Style style = mStyleService.getByName(name);
                    emitter.onNext(style);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doOnError(mView::showError)
                .subscribe(mView::setStyle);
    }

    @Override
    public void loadTag(String name) {
        Disposable observable = Observable
                .create((ObservableOnSubscribe<Tag>) emitter -> {
                    Tag tag = mTagService.getByName(name);
                    emitter.onNext(tag);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doOnError(mView::showError)
                .subscribe(mView::setTag);
    }

    @Override
    public void loadTags() {
        Disposable observable = Observable
                .create((ObservableOnSubscribe<List<Tag>>) emitter -> {
                    List<Tag> tags = mTagService.getAllTags();
                    emitter.onNext(tags);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        this::presentTagsToView,
                        error -> mView.showError(error)
                );
    }

    private void accept(Notification<Beer> x) {
        mView.hideLoading();
    }

    private void accept(Beer s) {
        mView.navigateToHome();
    }

    private void presentCountriesToView(List<Country> countries) {
        if (countries.isEmpty()) {
            mView.showEmptyList();
        } else {
            mView.showCountries(countries);
        }
    }

    private void presentStylesToView(List<Style> styles) {
        if (styles.isEmpty()) {
            mView.showEmptyList();
        } else {
            mView.showStyles(styles);
        }
    }

    private void presentTagsToView(List<Tag> tags) {
        if (tags.isEmpty()) {
            mView.showEmptyList();
        } else {
            mView.showTags(tags);
        }
    }

    @Override
    public void takePictureButtonIsClicked() {
        mView.presentOptionToTakePicture();
    }

}
