package com.beertag.android.repositories;

import com.beertag.android.http.HttpRequester;
import com.beertag.android.models.Drink;
import com.beertag.android.models.MyBeers;
import com.beertag.android.parsers.json.JsonParser;
import com.beertag.android.repositories.base.RatingRepository;
import com.beertag.android.repositories.base.Repository;

import java.io.IOException;
import java.util.List;

public class HttpRatingRepository implements RatingRepository {

    private final HttpRequester mHttpRequester;
    private final String mServerUrl;
    private final JsonParser<MyBeers> mJsonParser;

    public HttpRatingRepository(
            String serverUrl,
            HttpRequester httpRequester,
            JsonParser<MyBeers> jsonParser

    ) {
        mServerUrl = serverUrl + "/mybeers/";
        mHttpRequester = httpRequester;
        mJsonParser = jsonParser;
    }

    @Override
    public List<MyBeers> getAll() throws IOException {
        String url = mServerUrl;
        String jsonArray = mHttpRequester.get(url);
        return mJsonParser.fromJsonArray(jsonArray);
    }

    @Override
    public MyBeers add(MyBeers item) throws IOException {
        String requestBody = mJsonParser.toJson(item);
        //String responseBody =
        mHttpRequester.post(mServerUrl + "add/", requestBody);
        return null;//mJsonParser.fromJson(responseBody);
    }

    @Override
    public MyBeers delete(MyBeers item) throws IOException {
        return null;
    }

    @Override
    public MyBeers update(MyBeers item) throws IOException {
        String requestBody = mJsonParser.toJson(item);
        mHttpRequester.put(mServerUrl + "update/", requestBody);
        return null;//mJsonParser.fromJson(responseBody);
    }

    @Override
    public MyBeers getRatingByBeerId(int beerId) throws IOException {
        String url = mServerUrl + "rating/" + beerId;
        String json = mHttpRequester.get(url);
        return mJsonParser.fromJson(json);
    }

    @Override
    public List <MyBeers> getBeersByUserId(int userId) throws IOException {
        String url = mServerUrl + "beers/" + userId;
        String jsonArray = mHttpRequester.get(url);
        return mJsonParser.fromJsonArray(jsonArray);
    }

    @Override
    public MyBeers getByDrink(Drink drink) throws IOException {
        return null;
    }

    @Override
    public MyBeers getById (int beerId, int userId) throws IOException {
        String url = mServerUrl + beerId + "/" + userId;
        String json = mHttpRequester.get(url);
        return mJsonParser.fromJson(json);
    }
}
