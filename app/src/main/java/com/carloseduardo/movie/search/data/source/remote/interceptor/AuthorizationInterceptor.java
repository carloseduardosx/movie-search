package com.carloseduardo.movie.search.data.source.remote.interceptor;

import com.carloseduardo.movie.search.data.source.constants.API;
import com.carloseduardo.movie.search.helper.RequestHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {

    private final RequestHelper requestHelper = new RequestHelper();

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        request = request.newBuilder()
                .url(requestHelper.addParameter(request.url().toString(), "api_key", API.KEY))
                .build();

        return chain.proceed(request);
    }
}