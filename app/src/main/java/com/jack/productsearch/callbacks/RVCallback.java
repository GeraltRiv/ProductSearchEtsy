package com.jack.productsearch.callbacks;

import com.jack.productsearch.models.Product;

/**
 * Created by Jack on 12/11/2016.
 */

public interface RVCallback {
    void onProductAdded(Product product);
    void onProductDeleted(Product product);
    void onListEnd();
}
