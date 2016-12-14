package com.jack.productsearch.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by Jack on 12/10/2016.
 */

@Data
public class Image extends RealmObject implements Serializable {

    @SerializedName("url_570xN")
    private String thumbImageUrl;

    @SerializedName("url_fullxfull")
    private String imageUrl;

}
