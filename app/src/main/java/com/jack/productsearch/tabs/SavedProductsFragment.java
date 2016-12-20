package com.jack.productsearch.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jack.productsearch.MainActivity;
import com.jack.productsearch.ProductDetailFragment;
import com.jack.productsearch.ProductRVAdapter;
import com.jack.productsearch.R;
import com.jack.productsearch.callbacks.RVCallback;
import com.jack.productsearch.dao.Dao;
import com.jack.productsearch.models.Product;
import com.jack.productsearch.models.ProductState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SavedProductsFragment extends Fragment implements RVCallback{

    @BindView(R.id.saved_products_recycler_view)
    RecyclerView productsRV;

    View view;
    public ProductRVAdapter savedProductsAdapter;
    private Dao dao;
    private RVCallback rvCallback;

    public SavedProductsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_saved_products, container, false);
        ButterKnife.bind(this, view);
        dao = new Dao(getActivity());
        rvCallback = this;
        initProductList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((MainActivity)getActivity()).savedProductsAdapter != null && dao != null)
            ((MainActivity)getActivity()).savedProductsAdapter.setItems(dao.getSavedProductList());
    }

    private void initProductList() {
        productsRV.setHasFixedSize(true);
        productsRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        List<Product> productList = new ArrayList<>();
        for (Product product : dao.getSavedProductList())
            productList.add(product);
        ((MainActivity)getActivity()).savedProductsAdapter = new ProductRVAdapter(productList,
                getActivity(), ProductState.SAVED_PRODUCT, rvCallback);
        productsRV.setAdapter(((MainActivity)getActivity()).savedProductsAdapter);
    }

    @Override
    public void onProductAdded(Product product) {}

    @Override
    public void onProductDeleted(Product product) {
        ((MainActivity) getActivity()).savedProductsAdapter.deleteItem(product.getListingId());
        dao.deleteProduct(product.getListingId());
    }

    @Override
    public void onListEnd() {

    }
}
