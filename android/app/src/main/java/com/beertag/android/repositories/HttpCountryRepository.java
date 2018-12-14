package com.beertag.android.repositories;

import com.beertag.android.http.HttpRequester;
import com.beertag.android.models.Country;
import com.beertag.android.parsers.json.JsonParser;
import com.beertag.android.repositories.base.Repository;

import java.io.IOException;
import java.util.List;

public class HttpCountryRepository implements Repository {

    private final HttpRequester mHttpRequester;
    private final String mServerUrl;
    private final JsonParser<Country> mJsonParser;

    public HttpCountryRepository(
            String serverUrl,
            HttpRequester httpRequester,
            JsonParser<Country> jsonParser
    ) {
        mServerUrl = serverUrl+"/countries/";
        mHttpRequester = httpRequester;
        mJsonParser = jsonParser;
    }

    @Override
    public  List getAll() throws IOException {
        String jsonArray = mHttpRequester.get(mServerUrl);
        return mJsonParser.fromJsonArray(jsonArray);
    }

    @Override
    public Object add(Object item) throws IOException {
        return null;
    }

    @Override
    public Object delete(Object item) throws IOException {
        return null;
    }

    @Override
    public Object update(Object item) throws IOException {
        return null;
    }

    @Override
    public Object getById(int id) throws IOException {
        String url = mServerUrl  + "id/" + id;
        String json = mHttpRequester.get(url);
        return mJsonParser.fromJson(json);
    }

    @Override
    public Object getByName(String name) throws IOException {
        String url = mServerUrl + "name/" + name;
        String json = mHttpRequester.get(url);
        return mJsonParser.fromJson(json);
    }

}
