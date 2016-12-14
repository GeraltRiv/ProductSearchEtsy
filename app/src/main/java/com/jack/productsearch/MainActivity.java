package com.jack.productsearch;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jack.productsearch.callbacks.CategoryCallback;
import com.jack.productsearch.controllers.CategoryController;
import com.jack.productsearch.dao.Dao;
import com.jack.productsearch.models.Category;
import com.jack.productsearch.models.Product;
import com.jack.productsearch.tabs.TabsFragment;

import java.util.List;


public class MainActivity extends AppCompatActivity implements CategoryCallback{

    CategoryCallback categoryCallback;
    public ProductRVAdapter savedProductsAdapter;
    public Product detailProduct;
    private ProgressDialog progressDialog;
    public List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryCallback = this;
        progressDialog = ProgressDialog.show(this, getString(R.string.please_wait), "", true, false);
        new CategoryController(categoryCallback).getCategoryList();

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            this.finish();
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCategoriesLoaded(List<Category> categoryList) {
        progressDialog.cancel();
        new Dao(this).saveCategoryList(categoryList);
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, Fragment.instantiate(this, TabsFragment.class.getName()));
            ft.commit();
        }
    }
}
