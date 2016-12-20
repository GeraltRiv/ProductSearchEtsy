package com.jack.productsearch.connection;

import com.jack.productsearch.models.Category;
import com.jack.productsearch.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;


public class JsonAdapter {

    public List<Product> getProducts(Response result) {
        return (List<Product>) getListFromResponse(result, "results", Product.class);
    }

    public List<Category> getCategories(Response result) {
        return (List<Category>) getListFromResponse(result, "results", Category.class);
    }

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

    private List<?> getListFromResponse(Response response, String jsonArrayName, Class<?> cls) {
        List list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(stringFromResponse(response));
            JSONArray jsonArray = jsonObject.getJSONArray(jsonArrayName);
            for (int i = 0; i < jsonArray.length(); i++)
                list.add(cls.cast(GsonFactory.getGsonInstance().fromJson(jsonArray.get(i).toString(), cls)));
        } catch (JSONException e) {}
        return list;
    }

}
