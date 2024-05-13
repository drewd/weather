package com.dobson.weather.repository.remote.service;

import androidx.annotation.NonNull;

import com.dobson.weather.BuildConfig;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {
    public static final String PARAM_APP_ID = "appid";

    @Inject()
    public ApiKeyInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter(PARAM_APP_ID, BuildConfig.API_KEY)
                .build();
        return chain.proceed(request.newBuilder().url(url).build());
    }
}
