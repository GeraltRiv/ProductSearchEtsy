package com.jack.productsearch.connection;

import com.google.gson.Gson;

/**
 * Created by Vlad on 05.06.2015.
 */
public class GsonFactory {
    private static final Gson gson = new Gson();

    public static Gson getGsonInstance() {
        return gson;
    }
}
