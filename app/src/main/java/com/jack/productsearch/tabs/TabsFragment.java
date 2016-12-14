package com.jack.productsearch.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jack.productsearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.karim.MaterialTabs;

public class TabsFragment extends Fragment {

    @BindView(R.id.material_tabs)
    MaterialTabs tabs;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private View view;

    public TabsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabs, container, false);
        ButterKnife.bind(this, view);
        initAdapter();
        return view;
    }

    private void initAdapter() {
        final ProductPagerAdapter adapter = new ProductPagerAdapter(getChildFragmentManager());
        adapter.setContext(getActivity());
        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);
    }
}
