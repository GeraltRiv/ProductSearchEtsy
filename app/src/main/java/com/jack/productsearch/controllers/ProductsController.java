package com.jack.productsearch.controllers;

import android.util.Log;

import com.jack.productsearch.Constants;
import com.jack.productsearch.callbacks.ProductsCallback;
import com.jack.productsearch.connection.JsonAdapter;
import com.jack.productsearch.connection.RestAdapterSingleton;
import com.jack.productsearch.connection.RetryWithDelay;

import java.util.concurrent.TimeUnit;

import retrofit.client.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jack on 4/11/16.
 */
public class ProductsController {
    private final JsonAdapter jsonAdapter;
    private ProductsCallback productsCallback;

    public ProductsController(ProductsCallback productsCallback) {
        this.jsonAdapter = new JsonAdapter();
        this.productsCallback = productsCallback;
    }

    public void getProducts(String category, String searchText) {
        RestAdapterSingleton.getInstance().getRestAdapter().getProducts(Constants.API_KEY, category,
                searchText, Constants.INCLUDE_IMAGES)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(10, 1, TimeUnit.SECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Jack", e.toString());
                    }

                    @Override
                    public void onNext(Response response) {
                        productsCallback.onProductsLoad(jsonAdapter.getProducts(response));
                    }
                });
    }
}
