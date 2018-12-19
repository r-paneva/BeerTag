package com.beertag.android.views.beerDetails;

import com.beertag.android.models.Beer;
import com.beertag.android.models.User;

public interface ReviewListener {
    void onReview(int stars);

//    void onReview(int stars, User user, Beer beer);
}
