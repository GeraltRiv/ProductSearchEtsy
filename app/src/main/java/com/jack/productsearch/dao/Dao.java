package com.jack.productsearch.dao;

import android.content.Context;

import com.jack.productsearch.models.Category;
import com.jack.productsearch.models.Product;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Jack on 12/11/2016.
 */

public class Dao {

    Realm realm;

    public Dao(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = realm.getDefaultInstance();

    }

    public List<Product> getSavedProductList() {
        final RealmResults<Product> products = realm.where(Product.class).findAll();
        List<Product> productList = new ArrayList<>();
        productList.addAll(products);
        return productList;
    }

    public void saveProductList(List<Product> productList) {
        realm.beginTransaction();
        realm.copyToRealm(productList);
        realm.commitTransaction();
    }

    public boolean saveProduct(Product product) {
        if (!checkIfExists(product.getListingId())) {
            realm.beginTransaction();
            realm.copyToRealm(product);
            realm.commitTransaction();
            return true;
        }
        return false;
    }

    public List<Category> getCategoryList() {
        final RealmResults<Category> categories = realm.where(Category.class).findAll();
        List<Category> categoryList = new ArrayList<>();
        categoryList.addAll(categories);
        return categoryList;
    }

    public void saveCategoryList(List<Category> categoryList) {
        realm.beginTransaction();
        final RealmResults<Category> categories = realm.where(Category.class).findAll();
        categories.deleteAllFromRealm();
        realm.copyToRealm(categoryList);
        realm.commitTransaction();
    }

    public void deleteProduct(final long id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Product> result = realm.where(Product.class).equalTo("listingId", id).findAll();
                result.deleteAllFromRealm();
            }
        });
    }


    public boolean checkIfExists(long id) {
        RealmQuery<Product> query = realm.where(Product.class)
                .equalTo("listingId", id);
        return query.count() != 0;
    }
}
