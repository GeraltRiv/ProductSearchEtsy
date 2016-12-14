package com.jack.productsearch.callbacks;

import com.jack.productsearch.models.Product;

import java.util.List;

/**
 * Created by Jack on 12/10/2016.
 */

public interface ProductsCallback {
    void onProductsLoad(List<Product> productList);
}
