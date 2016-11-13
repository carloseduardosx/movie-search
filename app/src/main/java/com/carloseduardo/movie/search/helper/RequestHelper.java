package com.carloseduardo.movie.search.helper;

public class RequestHelper {

    public String addParameter(String url, String key, String value) {

        String parameter = String.format("?%s=%s", key, value);
        parameter = url.contains("?") ? parameter.replace("?", "&") : parameter;

        return url + parameter;
    }
}