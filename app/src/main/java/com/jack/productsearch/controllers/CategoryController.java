package com.jack.productsearch.controllers;

import android.util.Log;

import com.jack.productsearch.Constants;
import com.jack.productsearch.callbacks.CategoryCallback;
import com.jack.productsearch.connection.JsonAdapter;
import com.jack.productsearch.connection.RestAdapterSingleton;
import com.jack.productsearch.connection.RetryWithDelay;

import java.util.concurrent.TimeUnit;

import retrofit.client.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jack on 12/11/2016.
 */

public class CategoryController {
    private final JsonAdapter jsonAdapter;
    private CategoryCallback categoryCallback;

    public CategoryController(CategoryCallback categoryCallback) {
        this.jsonAdapter = new JsonAdapter();
        this.categoryCallback = categoryCallback;
    }

    public void getCategoryList() {
        RestAdapterSingleton.getInstance().getRestAdapter().getCategories(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(10, 600, TimeUnit.MILLISECONDS))
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
                        categoryCallback.onCategoriesLoaded(jsonAdapter.getCategories(response));
                    }
                });
    }
}
