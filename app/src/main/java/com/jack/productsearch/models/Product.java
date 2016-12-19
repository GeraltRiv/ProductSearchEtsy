package com.jack.productsearch.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import lombok.Data;

/**
 * Created by Jack on 12/10/2016.
 */

@Data
public class Product extends RealmObject implements Serializable {

    @SerializedName("listing_id")
    private long listingId;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private double price;

    @SerializedName("currency_code")
    private String currencyCode;

    @SerializedName("url")
    private String url;

    @SerializedName("MainImage")
    private Image image;

    @Ignore
    private ProductState productState;
}
