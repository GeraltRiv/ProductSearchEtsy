package com.jack.productsearch.tabs;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jack.productsearch.R;

import io.karim.MaterialTabs;

/**
 * Created by Jack on 12/10/2016.
 */

public class ProductPagerAdapter extends FragmentPagerAdapter implements MaterialTabs.CustomTabProvider {

    private static final String TAG = "PagerAdapter";
    private final String[] TITLES = {"Search", "My products"};

    private final int[] UNSELECTED_ICONS = {R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private final int[] SELECTED_ICONS = {R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private Context context;

    public ProductPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            default:
                return new SearchFragment();
            case 1:
                return new SavedProductsFragment();
        }
    }

    @Override
    public View getCustomTabView(ViewGroup parent, int position) {
        TextView textView = new TextView(context);
        textView.setText(TITLES[position]);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(18);
        return textView;
    }

    @Override
    public void onCustomTabViewSelected(View view, int position, boolean alreadySelected) {
        Log.i(TAG, "custom tab view selected with position = " + position);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(SELECTED_ICONS[position]);
        }
    }

    @Override
    public void onCustomTabViewUnselected(View view, int position, boolean alreadyUnselected) {
        Log.i(TAG, "custom tab view unselected with position = " + position);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}