package com.jack.productsearch.callbacks;

import com.jack.productsearch.models.Product;

import java.util.List;

/**
 * Created by Jack on 12/14/2016.
 */

public interface PaginationCallback {
    void onPaginationProductsLoad(List<Product> productList);
}
