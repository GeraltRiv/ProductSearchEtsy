package com.jack.productsearch.controllers;

import android.util.Log;

import com.jack.productsearch.Constants;
import com.jack.productsearch.callbacks.PaginationCallback;
import com.jack.productsearch.connection.JsonAdapter;
import com.jack.productsearch.connection.RestAdapterSingleton;
import com.jack.productsearch.connection.RetryWithDelay;

import java.util.concurrent.TimeUnit;

import retrofit.client.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jack on 12/14/2016.
 */

public class PaginationController {
    private final JsonAdapter jsonAdapter;
    private PaginationCallback paginationCallback;

    public PaginationController(PaginationCallback paginationCallback) {
        this.jsonAdapter = new JsonAdapter();
        this.paginationCallback = paginationCallback;
    }

    public void getProducts(String category, String searchText, int offset) {
        RestAdapterSingleton.getInstance().getRestAdapter().getPaginationProducts(Constants.API_KEY, category,
                searchText, Constants.INCLUDE_IMAGES, offset)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(10, 1, TimeUnit.SECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Jack", e.toString());
                    }

                    @Override
                    public void onNext(Response response) {
                        paginationCallback.onPaginationProductsLoad(jsonAdapter.getProducts(response));
                    }
                });
    }
}
