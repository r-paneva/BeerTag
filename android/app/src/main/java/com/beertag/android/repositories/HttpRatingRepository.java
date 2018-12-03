package com.beertag.android.repositories;

import com.beertag.android.http.HttpRequester;
import com.beertag.android.parsers.base.JsonParser;
import com.beertag.android.repositories.base.Repository;

import java.io.IOException;
import java.util.List;

public class HttpRatingRepository<T> implements Repository<T> {

    private final HttpRequester mHttpRequester;
    private final String mServerUrl;
    private final JsonParser<T> mJsonParser;

    public HttpRatingRepository(
            String serverUrl,
            HttpRequester httpRequester,
            JsonParser<T> jsonParser

    ) {
        mServerUrl = serverUrl;
        mHttpRequester = httpRequester;
        mJsonParser = jsonParser;
    }

    @Override
    public List<T> getAll() throws IOException {
        String url = mServerUrl + "/rating";
        String jsonArray = mHttpRequester.get(url);
        return mJsonParser.fromJsonArray(jsonArray);
    }

    @Override
    public T add(T item) throws IOException {
        String requestBody = mJsonParser.toJson(item);
        //String responseBody =
        mHttpRequester.post(mServerUrl + "/rating/add", requestBody);
        return null;//mJsonParser.fromJson(responseBody);
    }

    @Override
    public T delete(T item) throws IOException {
        return null;
    }

    //// ??????
    public T getRaitingById(int userId, int beerId) throws IOException {
        String url = mServerUrl + "/" + userId + "/" + beerId;
        String json = mHttpRequester.get(url);
        return mJsonParser.fromJson(json);
    }

    @Override
    public T update(T item) throws IOException {
        String requestBody = mJsonParser.toJson(item);
        mHttpRequester.put(mServerUrl + "/rating/update", requestBody);
        return null;//mJsonParser.fromJson(responseBody);
    }

    @Override
    public T getById(int id) throws IOException {
        return null;
    }

    @Override
    public T getByName(String username) throws IOException {
        return null;
    }
}
