package com.beertag.android.repositories;

import com.beertag.android.http.HttpRequester;
import com.beertag.android.models.Beer;
import com.beertag.android.parsers.base.JsonParser;
import com.beertag.android.repositories.base.Repository;

import java.io.IOException;
import java.util.List;

public class HttpBeerRepository implements Repository {

    private final HttpRequester mHttpRequester;
    private final String mServerUrl;
    private final JsonParser<Beer> mJsonParser;

    public HttpBeerRepository(
            String serverUrl,
            HttpRequester httpRequester,
            JsonParser<Beer> jsonParser
    ) {
        mServerUrl = serverUrl+"/beers/";
        mHttpRequester = httpRequester;
        mJsonParser = jsonParser;
    }

    @Override
    public List getAll() throws IOException {
        String jsonArray = mHttpRequester.get(mServerUrl);
        return mJsonParser.fromJsonArray(jsonArray);
    }

    @Override
    public Object add(Object item) throws IOException {
        String requestBody = mJsonParser.toJson((Beer) item);
        //String responseBody =
        mHttpRequester.post(mServerUrl, requestBody);
        return null;//mJsonParser.fromJson(responseBody);
    }

    @Override
    public Object delete(Object item) throws IOException {
        String requestBody = mJsonParser.toJson((Beer) item);
        //String responseBody =
        mHttpRequester.delete(mServerUrl, requestBody);
        return null;//mJsonParser.fromJson(responseBody);
    }

    @Override
    public Object update(Object item) throws IOException {
        String requestBody = mJsonParser.toJson((Beer) item);
        mHttpRequester.put(mServerUrl , requestBody);
        return null;//mJsonParser.fromJson(responseBody);
    }

    @Override
    public Object getById(int id) throws IOException {
        String url = mServerUrl + id;
        String json = mHttpRequester.get(url);
        return mJsonParser.fromJson(json);
    }

    @Override
    public Object getByName(String username) throws IOException {
        String url = mServerUrl + "username/" + username;
        String json = mHttpRequester.get(url);
        return mJsonParser.fromJson(json);
    }

}
