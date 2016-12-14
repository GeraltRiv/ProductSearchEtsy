package com.jack.productsearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jack.productsearch.models.Image;
import com.jack.productsearch.models.Product;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductDetailFragment extends Fragment {

    private View view;

    private Product product;

    @BindView(R.id.product_image_detail_fragment)
    ImageView productImage;

    @BindView(R.id.product_title_detail_fragment)
    TextView title;

    @BindView(R.id.product_price_detail_fragment)
    TextView price;

    @BindView(R.id.product_description_detail_product)
    TextView description;

    public ProductDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(this, view);
        product = ((MainActivity) getActivity()).detailProduct;
        initUI();
        return view;
    }

    private void initUI() {
        Picasso.with(getActivity()).load(product.getImage().getImageUrl()).into(productImage);
        title.setText(product.getTitle());
        price.setText(product.getPrice() + " " + product.getCurrencyCode());
        description.setText(product.getDescription());
    }

}
