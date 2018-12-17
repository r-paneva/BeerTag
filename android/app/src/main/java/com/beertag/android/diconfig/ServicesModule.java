package com.beertag.android.diconfig;

import com.beertag.android.models.Beer;
import com.beertag.android.models.Country;
import com.beertag.android.models.Style;
import com.beertag.android.models.Tag;
import com.beertag.android.models.User;
import com.beertag.android.repositories.base.RatingRepository;
import com.beertag.android.repositories.base.Repository;
import com.beertag.android.services.HttpBeersService;
import com.beertag.android.services.HttpCountryService;
import com.beertag.android.services.HttpRatingVoteService;
import com.beertag.android.services.HttpStyleService;
import com.beertag.android.services.HttpTagService;
import com.beertag.android.services.HttpUsersService;
import com.beertag.android.services.base.BeersService;
import com.beertag.android.services.base.CountryService;
import com.beertag.android.services.base.RatingVoteService;
import com.beertag.android.services.base.StyleService;
import com.beertag.android.services.base.TagService;
import com.beertag.android.services.base.UsersService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {
    @Provides
    public BeersService BeersService(Repository<Beer> repository) {
        return new HttpBeersService(repository);
    }

    @Provides
    RatingVoteService RatingVoteService(RatingRepository repository) {
        return new HttpRatingVoteService(repository);
    }

    @Provides
    public UsersService UsersService(Repository<User> repository) {
        return new HttpUsersService(repository);
    }

    @Provides
    CountryService CountryService(Repository<Country> repository) {
        return new HttpCountryService(repository);
    }

    @Provides
    StyleService StyleService(Repository<Style> repository) {
        return new HttpStyleService(repository);
    }

    @Provides
    TagService TagService(Repository<Tag> repository) {
        return new HttpTagService(repository);
    }

}
