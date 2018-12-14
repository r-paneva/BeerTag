package com.beertag.android.diconfig;

import com.beertag.android.models.Beer;
import com.beertag.android.models.Country;
import com.beertag.android.models.RatingVote;
import com.beertag.android.models.Style;
import com.beertag.android.models.Tag;
import com.beertag.android.models.User;
import com.beertag.android.parsers.json.GsonJsonParser;
import com.beertag.android.parsers.json.JsonParser;

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

    @Provides
    public JsonParser<Style> StyleJsonParser() {
        return new GsonJsonParser<>(Style.class, Style[].class);
    }

    @Provides
    public JsonParser<Tag> TagJsonParser() {
        return new GsonJsonParser<>(Tag.class, Tag[].class);
    }
}
