package com.beertag.android.http;

import java.io.IOException;

public interface HttpRequester {
    String get(String url) throws IOException;

    String post(String url, String body) throws IOException;

    String put(String url, String body) throws IOException;

    String delete(String url, String body) throws IOException;
}
