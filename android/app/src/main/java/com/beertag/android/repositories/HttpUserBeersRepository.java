package com.beertag.android.repositories;

import com.beertag.android.http.HttpRequester;
import com.beertag.android.models.Drink;
import com.beertag.android.models.UserBeers;
import com.beertag.android.parsers.json.JsonParser;
import com.beertag.android.repositories.base.UserBeersRepository;

import java.io.IOException;
import java.util.List;

public class HttpUserBeersRepository implements UserBeersRepository {

    private final HttpRequester mHttpRequester;
    private final String mServerUrl;
    private final JsonParser<UserBeers> mJsonParser;

    public HttpUserBeersRepository(
            String serverUrl,
            HttpRequester httpRequester,
            JsonParser<UserBeers> jsonParser

    ) {
        mServerUrl = serverUrl + "/userbeers/";
        mHttpRequester = httpRequester;
        mJsonParser = jsonParser;
    }

    @Override
    public List<UserBeers> getAll() throws IOException {
        String url = mServerUrl;
        String jsonArray = mHttpRequester.get(url);
        return mJsonParser.fromJsonArray(jsonArray);
    }

    @Override
    public UserBeers add(UserBeers item) throws IOException {
        String requestBody = mJsonParser.toJson(item);
        //String responseBody =
        mHttpRequester.post(mServerUrl + "add/", requestBody);
        return null;//mJsonParser.fromJson(responseBody);
    }

    @Override
    public UserBeers delete(UserBeers item) throws IOException {
        return null;
    }

    @Override
    public UserBeers update(UserBeers item) throws IOException {
        String requestBody = mJsonParser.toJson(item);
        mHttpRequester.put(mServerUrl + "update/", requestBody);
        return null;//mJsonParser.fromJson(responseBody);
    }

    @Override
    public UserBeers getRatingByBeerId(int beerId) throws IOException {
        String url = mServerUrl + "rating/" + beerId;
        String json = mHttpRequester.get(url);
        return mJsonParser.fromJson(json);
    }

    @Override
    public List <UserBeers> getBeersByUserId(int userId) throws IOException {
        String url = mServerUrl + "user/" + userId;
        String jsonArray = mHttpRequester.get(url);
        return mJsonParser.fromJsonArray(jsonArray);
    }

    @Override
    public UserBeers getByDrink(Drink drink) throws IOException {
        return null;
    }

    @Override
    public UserBeers getById (int beerId, int userId) throws IOException {
        String url = mServerUrl + beerId + "/" + userId;
        String json = mHttpRequester.get(url);
        return mJsonParser.fromJson(json);
    }
}
