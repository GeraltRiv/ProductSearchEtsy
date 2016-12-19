package com.jack.productsearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.widgets.Dialog;
import com.github.mrengineer13.snackbar.SnackBar;
import com.jack.productsearch.dao.Dao;
import com.jack.productsearch.models.Image;
import com.jack.productsearch.models.Product;
import com.jack.productsearch.models.ProductState;
import com.squareup.picasso.Picasso;

import javax.xml.datatype.Duration;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductDetailFragment extends Fragment {

    private View view;

    private Product product;
    private Dao dao;

    @BindView(R.id.product_image_detail_fragment)
    ImageView productImage;

    @BindView(R.id.product_title_detail_fragment)
    TextView title;

    @BindView(R.id.product_price_detail_fragment)
    TextView price;

    @BindView(R.id.product_description_detail_product)
    TextView description;

    @BindView(R.id.product_save_detail_fragment)
    Button saveDeleteButton;

    public ProductDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(this, view);
        dao = new Dao(getActivity());
        product = ((MainActivity) getActivity()).detailProduct;
        initUI();
        return view;
    }

    private void initUI() {
        Picasso.with(getActivity()).load(product.getImage().getImageUrl()).into(productImage);
        title.setText(product.getTitle());
        price.setText(product.getPrice() + " " + product.getCurrencyCode());
        description.setText(product.getDescription());
        saveDeleteButton.setOnClickListener(saveDeleteOnClickListener);
        saveDeleteButton.setText( product.getProductState() == ProductState.SAVED_PRODUCT
                                ? getString(R.string.delete)
                                : getString(R.string.save));
    }

    View.OnClickListener saveDeleteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SnackBar.Builder snackBar = new SnackBar.Builder(getActivity().getApplicationContext(), view);
            if (product.getProductState() == ProductState.SAVED_PRODUCT) {
                final Dialog dialog = new Dialog(getActivity(), getString(R.string.warning), "\n\n");
                dialog.addCancelButton(getString(R.string.cancel));
                dialog.show();
                dialog.getButtonAccept().setText(getString(R.string.delete));
                dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dao.deleteProduct(product.getListingId());
                        dialog.cancel();
                        getActivity().onBackPressed();
                    }
                });
                dialog.setMessage(getString(R.string.delete_item_dialog));
            }
            else {
                snackBar.withMessage(getString(R.string.this_product_saved)).show();
                dao.saveProduct(product);
            }
        }
    };

}
