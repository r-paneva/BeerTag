package com.beertag.android.diconfig;

import com.beertag.android.models.Beer;
import com.beertag.android.models.Country;
import com.beertag.android.models.RatingVote;
import com.beertag.android.models.User;
import com.beertag.android.repositories.HttpBeerRepository;
import com.beertag.android.repositories.HttpCountryRepository;
import com.beertag.android.repositories.base.Repository;
import com.beertag.android.services.HttpBeersService;
import com.beertag.android.services.HttpCountryService;
import com.beertag.android.services.HttpRatingVoteService;
import com.beertag.android.services.HttpUsersService;
import com.beertag.android.services.base.BeersService;
import com.beertag.android.services.base.CountryService;
import com.beertag.android.services.base.RatingVoteService;
import com.beertag.android.services.base.UsersService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {
    @Provides
    public BeersService BeersService(HttpBeerRepository repository) {
        return new HttpBeersService(repository);
    }

    @Provides
    public RatingVoteService RatingVoteService(Repository<RatingVote> repository) {
        return new HttpRatingVoteService(repository);
    }

    @Provides
    public UsersService UsersService(Repository<User> repository) {
        return new HttpUsersService(repository);
    }

    @Provides
    public CountryService CountryService(HttpCountryRepository repository) {
        return new HttpCountryService(repository);
    }
}
