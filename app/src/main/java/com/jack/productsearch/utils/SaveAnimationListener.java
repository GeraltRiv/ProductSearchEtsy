package com.jack.productsearch.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jack.productsearch.R;

/**
 * Created by Jack on 12/12/2016.
 */

public class SaveAnimationListener implements Animation.AnimationListener {

    private ImageView view;
    private Context context;

    public void setImage(ImageView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void onAnimationEnd(Animation animation) {
        Animation animZoomOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
        this.view.startAnimation(animZoomOut);
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}