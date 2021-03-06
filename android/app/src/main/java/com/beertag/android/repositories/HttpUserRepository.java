package com.beertag.android.repositories;

import com.beertag.android.http.HttpRequester;
import com.beertag.android.models.User;
import com.beertag.android.parsers.json.JsonParser;
import com.beertag.android.repositories.base.Repository;

import java.io.IOException;
import java.util.List;

public class HttpUserRepository implements Repository {

    private final HttpRequester mHttpRequester;
    private final String mServerUrl;
    private final JsonParser<User> mJsonParser;

    public HttpUserRepository(
            String serverUrl,
            HttpRequester httpRequester,
            JsonParser<User> jsonParser
    ) {
        mServerUrl = serverUrl + "/users/";
        mHttpRequester = httpRequester;
        mJsonParser = jsonParser;
    }

    @Override
    public List getAll() throws IOException {
        String jsonArray = mHttpRequester.get(mServerUrl);
        return mJsonParser.fromJsonArray(jsonArray);
    }

    @Override
    public String add(Object item) throws IOException {
        String requestBody = mJsonParser.toJson((User) item);
        //String responseBody =
        mHttpRequester.post(mServerUrl + "add", requestBody);
        return null;//mJsonParser.fromJson(responseBody);
    }

    @Override
    public Object delete(Object item) throws IOException {
        String requestBody = mJsonParser.toJson((User) item);
        //String responseBody =
        mHttpRequester.delete(mServerUrl , requestBody);
        return null;//mJsonParser.fromJson(responseBody);
    }

    @Override
    public Object update(Object item) throws IOException {
        String requestBody = mJsonParser.toJson((User) item);
        mHttpRequester.put(mServerUrl, requestBody);
        return null;//mJsonParser.fromJson(responseBody, Beer.class);
    }

    @Override
    public Object getById(int id) throws IOException {
        String url = mServerUrl + "id/" + id;
        String json = mHttpRequester.get(url);
        return mJsonParser.fromJson(json);
    }

    @Override
    public Object getByName(String username) throws IOException {
        String url = mServerUrl + username;
        String json = mHttpRequester.get(url);
        return mJsonParser.fromJson(json);
    }

}
