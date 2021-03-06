package com.beertag.android.diconfig;

import com.beertag.android.http.HttpRequester;
import com.beertag.android.models.Beer;
import com.beertag.android.models.Country;
import com.beertag.android.models.UserBeers;
import com.beertag.android.models.Style;
import com.beertag.android.models.Tag;
import com.beertag.android.models.User;
import com.beertag.android.parsers.json.JsonParser;
import com.beertag.android.repositories.HttpBeerRepository;
import com.beertag.android.repositories.HttpCountryRepository;
import com.beertag.android.repositories.HttpUserBeersRepository;
import com.beertag.android.repositories.HttpStyleRepository;
import com.beertag.android.repositories.HttpTagRepository;
import com.beertag.android.repositories.HttpUserRepository;
import com.beertag.android.repositories.base.UserBeersRepository;
import com.beertag.android.repositories.base.Repository;
;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class RepositoriesModule {
    @Provides
    @Singleton
    Repository<Beer> BeerRepository(
            @Named("baseServerUrl") String baseServerUrl,
            HttpRequester httpRequester,
            JsonParser<Beer> jsonParser
    ) {
        String url = baseServerUrl;// + "/beers";
        return new HttpBeerRepository(url, httpRequester, jsonParser);
    }

    @Provides
    @Singleton
    public UserBeersRepository RatingVoteRepository(
            @Named("baseServerUrl") String baseServerUrl,
            HttpRequester httpRequester,
            JsonParser<UserBeers> jsonParser
    ) {
        String url = baseServerUrl;
        return new HttpUserBeersRepository(url, httpRequester, jsonParser);
    }

    @Provides
    @Singleton
    public Repository<User> UserRepository(
            @Named("baseServerUrl") String baseServerUrl,
            HttpRequester httpRequester,
            JsonParser<User> jsonParser
    ) {
        String url = baseServerUrl; // + "/Users";
        return new HttpUserRepository(url, httpRequester, jsonParser);
    }

    @Provides
    @Singleton
    public Repository<Country> CountryRepository(
            @Named("baseServerUrl") String baseServerUrl,
            HttpRequester httpRequester,
            JsonParser<Country> jsonParser
    ) {
        String url = baseServerUrl; // + "/countries";
        return new HttpCountryRepository(url, httpRequester, jsonParser);
    }

    @Provides
    @Singleton
    public Repository<Style> StyleRepository(
            @Named("baseServerUrl") String baseServerUrl,
            HttpRequester httpRequester,
            JsonParser<Style> jsonParser
    ) {
        String url = baseServerUrl; // + "/styles";
        return new HttpStyleRepository(url, httpRequester, jsonParser);
    }

    @Provides
    @Singleton
    public Repository<Tag> TagRepository(
            @Named("baseServerUrl") String baseServerUrl,
            HttpRequester httpRequester,
            JsonParser<Tag> jsonParser
    ) {
        String url = baseServerUrl; // + "/tags";
        return new HttpTagRepository(url, httpRequester, jsonParser);
    }
}
