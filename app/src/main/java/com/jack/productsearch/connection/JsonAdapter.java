package com.jack.productsearch.connection;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jack.productsearch.models.Category;
import com.jack.productsearch.models.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;


public class JsonAdapter {

    public List<Product> getProducts(Response result) {
        List<Product> productList = null;
        try {
            JSONObject jsonObject = new JSONObject(stringFromResponse(result));
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            productList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                productList.add(GsonFactory.getGsonInstance().fromJson(jsonArray.get(i).toString(), Product.class));
            }
        } catch (Exception e){}
        
        return productList;
    }

    public List<Category> getCategories(Response result) {
        List<Category> categoryList = null;
        try {
            JSONObject jsonObject = new JSONObject(stringFromResponse(result));
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            categoryList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                categoryList.add(GsonFactory.getGsonInstance().fromJson(jsonArray.get(i).toString(), Category.class));
            }
        } catch (Exception e){}

        return categoryList;
    }

    //
//    public List<Service> getServices(Response response){
//        try {
//            JSONObject jsonObject = new JSONObject(stringFromResponse(response));
//            JSONArray jsonArray = jsonObject.getJSONArray("services");
//            List<Service> serviceList = new ArrayList<Service>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                serviceList.add(GsonFactory.getGsonInstance().fromJson(jsonArray.get(i).toString(), Service.class));
//            }
//            return serviceList;
//        } catch (Exception e) {
//            Log.d("Jack", e.toString());
//            return null;
//        }
//    }
//
//    public String getReportId(Response response){
//        JsonObject accRespon = GsonFactory.getGsonInstance().fromJson(stringFromResponse(response), JsonObject.class);
//        String userObject = accRespon.get("reportId").toString();
//        return userObject;
//    }
//
//    public int getStatusCode(Response response) {
//        JsonObject accRespon = GsonFactory.getGsonInstance().fromJson(stringFromResponse(response), JsonObject.class);
//        JsonObject statusJsn = GsonFactory.getGsonInstance().fromJson(accRespon.get("status"), JsonObject.class);
//        int userObject = statusJsn.get("statusCode").getAsInt();
//        return userObject;
//    }
//
//    public GeneralReportModel getAllReports(Response response){
//        GeneralReportModel generalReportModel = GsonFactory.getGsonInstance().fromJson(stringFromResponse(response), GeneralReportModel.class);
//        JsonObject generalResponse = GsonFactory.getGsonInstance().fromJson(stringFromResponse(response), JsonObject.class);
//        JsonObject statusJsn = GsonFactory.getGsonInstance().fromJson(generalResponse.get("status"), JsonObject.class);
//        generalReportModel.setStatusCode(statusJsn.get("statusCode").getAsInt());
//        generalReportModel.setMessage(statusJsn.get("message").toString());
//        return generalReportModel;
//    }
//
////    {"sessionToken":"2d9169f8-977f-4667-9d05-40a54d1ad775","user":{"name":"vabaa svsghs","id":2,
////       "plateNumber":"AH 777","email":"qqq@qqq.com","phone":"0856321669","password":null,"vin":null,
////       "car":"1948, Citroen, 2CV"},"reports":null,"damageTypes":null,"status":{"statusCode":0,"message":null}}
//
//    public Account getUpdatedProfile(Response response) {
//        JsonObject respJson = GsonFactory.getGsonInstance().fromJson(stringFromResponse(response), JsonObject.class);
//        JsonObject statusJsn = GsonFactory.getGsonInstance().fromJson(respJson.get("status"), JsonObject.class);
//        Account acc = GsonFactory.getGsonInstance().fromJson(respJson.get("user").toString(), Account.class);
//        acc.setStatusCode(statusJsn.get("statusCode").getAsString());
//        acc.setSessionToken(respJson.get("sessionToken").getAsString());
//        return acc;
//    }
//
//    public CarListForDAO getCarsList(Response response) {
//        return GsonFactory.getGsonInstance().fromJson(stringFromResponse(response), CarListForDAO.class);
//    }
//
    public String stringFromResponse(Response response) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    response.getBody().in()));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = sb.toString();
        return result;
    }

}
