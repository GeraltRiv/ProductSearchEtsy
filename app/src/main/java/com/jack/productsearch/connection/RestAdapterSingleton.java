package com.jack.productsearch.connection;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestAdapterSingleton {

    API service;
    public static final String BASE_URL = "https://openapi.etsy.com/v2";
    private static RestAdapterSingleton mInstance = null;

    private RestAdapterSingleton() {
        RestAdapter restAdapter = providesRestAdapter(GsonFactory.getGsonInstance());
        service = restAdapter.create(API.class);
    }

    public static RestAdapterSingleton getInstance() {
        if (mInstance == null) {
            mInstance = new RestAdapterSingleton();
        }
        return mInstance;
    }

    public API getRestAdapter() {
        return service;
    }

    private RestAdapter providesRestAdapter(Gson gson) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient))
                .build();
    }

}
