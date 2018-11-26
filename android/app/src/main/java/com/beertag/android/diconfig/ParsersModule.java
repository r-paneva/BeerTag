package com.beertag.android.diconfig;

import com.beertag.android.models.Beer;
import com.beertag.android.models.Country;
import com.beertag.android.models.RatingVote;
import com.beertag.android.models.User;
import com.beertag.android.parsers.GsonJsonParser;
import com.beertag.android.parsers.base.JsonParser;

import dagger.Module;
import dagger.Provides;

@Module
public class ParsersModule {
    @Provides
    public JsonParser<User> UserJsonParser() {
        return new GsonJsonParser<>(User.class, User[].class);
    }

    @Provides
    public JsonParser<Beer> MessageJsonParser() {
        return new GsonJsonParser<>(Beer.class, Beer[].class);
    }

    @Provides
    public JsonParser<RatingVote> RatingVoteJsonParser() {
        return new GsonJsonParser<>(RatingVote.class, RatingVote[].class);
    }

    @Provides
    public JsonParser<Country> CountryJsonParser() {
        return new GsonJsonParser<>(Country.class, Country[].class);
    }

}
