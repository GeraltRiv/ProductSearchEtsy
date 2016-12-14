package com.jack.productsearch.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jack on 12/10/2016.
 */

@Data
public class Category extends RealmObject implements Serializable{

    @SerializedName("category_id")
    private long categoryId;

    @SerializedName("name")
    private String name;

    @SerializedName("meta_title")
    private String metaTitle;

    @SerializedName("meta_keywords")
    private String metaKeywords;

    @SerializedName("meta_description")
    private String metaDescription;

    @SerializedName("page_description")
    private String pageDescription;

    @SerializedName("page_title")
    private String pageTitle;

    @SerializedName("category_name")
    private String categoryName;

    @SerializedName("short_name")
    private String shortName;

    @SerializedName("long_name")
    private String longName;

    @SerializedName("num_children")
    private String numChildren;
}
