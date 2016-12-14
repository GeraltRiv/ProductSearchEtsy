package com.jack.productsearch.callbacks;

import com.jack.productsearch.models.Category;

import java.util.List;

/**
 * Created by Jack on 12/11/2016.
 */

public interface CategoryCallback {
    void onCategoriesLoaded(List<Category> categoryList);
}
