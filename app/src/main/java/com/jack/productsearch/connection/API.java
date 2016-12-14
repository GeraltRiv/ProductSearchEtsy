package com.jack.productsearch.connection;

import java.util.List;
import java.util.Map;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import rx.Observable;

public interface API {

    @GET("/taxonomy/categories")
    Observable<Response> getCategories(@Query("api_key") String apiKey);

    @GET("/listings/active")
    Observable<Response> getProducts(@Query("api_key") String apiKey,
                                     @Query("category") String category,
                                     @Query("keywords") String searchText,
                                     @Query("includes") String mainImage);

    @GET("/listings/active")
    Observable<Response> getPaginationProducts(@Query("api_key") String apiKey,
                                               @Query("category") String category,
                                               @Query("keywords") String searchText,
                                               @Query("includes") String mainImage,
                                               @Query("offset") int offset);


}
