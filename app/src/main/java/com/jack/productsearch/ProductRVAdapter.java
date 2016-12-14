package com.jack.productsearch;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.widgets.Dialog;
import com.jack.productsearch.callbacks.RVCallback;
import com.jack.productsearch.dao.Dao;
import com.jack.productsearch.models.Image;
import com.jack.productsearch.models.Product;
import com.jack.productsearch.models.ProductState;
import com.jack.productsearch.utils.SaveAnimationListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jack on 12/10/2016.
 */

public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.ViewHolder> {

    private List<Product> productList;
    private Context context;
    private Dao dao;
    private ProductState productListState;
    private RVCallback rvCallback;
    private SaveAnimationListener saveAnimationListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_card_view)
        public View productView;

        @BindView(R.id.title_product)
        public TextView titleProduct;

        @BindView(R.id.product_image)
        public ImageView productImage;

        @BindView(R.id.save_delete_product_button)
        public ImageView saveDeleteButton;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public ProductRVAdapter(List<Product> productList, Context context, ProductState productListState, RVCallback rvCallback) {
        this.productList = productList;
        this.context = context;
        this.productListState = productListState;
        this.rvCallback = rvCallback;
        dao = new Dao(context);
        saveAnimationListener = new SaveAnimationListener();
    }

    @Override
    public ProductRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context)
                .load(productList.get(position).getImage().getThumbImageUrl())
                .into(holder.productImage);
        holder.titleProduct.setText(productList.get(position).getTitle());
        if (productListState == ProductState.SAVED_PRODUCT)
            holder.saveDeleteButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_delete_white_24dp));
        else
            holder.saveDeleteButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
        holder.saveDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productListState == ProductState.UNSAVED_PRODUCT) {
                    holder.saveDeleteButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_full_white_24dp));
                    rvCallback.onProductAdded(productList.get(position));
                    saveAnimationListener.setImage(holder.saveDeleteButton, context);
                    Animation animZoomin = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
                    animZoomin.setAnimationListener(saveAnimationListener);
                    holder.saveDeleteButton.startAnimation(animZoomin);
                } else {
                    final Dialog dialog = new Dialog(context, "Error", "\n\n");
                    dialog.addCancelButton(context.getString(R.string.cancel));
                    dialog.show();
                    dialog.getButtonAccept().setText(context.getString(R.string.delete));
                    dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rvCallback.onProductDeleted(productList.get(position));
                            dialog.cancel();
                        }
                    });
                    dialog.setMessage(context.getString(R.string.delete_item_dialog));
                }
            }
        });

        holder.productView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvCallback.onProduсtDetailButtonClicked(productList.get(position));
            }
        });

        Log.d("Jack", "pos " + position + " size " + productList.size());
        if (position == productList.size() - 1)
            rvCallback.onListEnd();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void addItems(List<Product> productList) {
        this.productList.addAll(productList);
        this.notifyDataSetChanged();
    }

    public void setItems(List<Product> productList) {
        this.productList = productList;
        this.notifyDataSetChanged();
    }

    public void addItem(Product product) {
        this.productList.add(product);
        this.notifyDataSetChanged();
    }

    public void deleteItem(long id) {
        for (Product product : productList)
            if (product.getListingId() == id) {
                this.productList.remove(product);
                this.notifyDataSetChanged();
            }

    }
}
