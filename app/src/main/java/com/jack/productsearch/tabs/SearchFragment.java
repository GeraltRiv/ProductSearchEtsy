package com.jack.productsearch.tabs;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.jack.productsearch.MainActivity;
import com.jack.productsearch.ProductDetailFragment;
import com.jack.productsearch.ProductRVAdapter;
import com.jack.productsearch.R;
import com.jack.productsearch.callbacks.PaginationCallback;
import com.jack.productsearch.callbacks.ProductsCallback;
import com.jack.productsearch.callbacks.RVCallback;
import com.jack.productsearch.controllers.PaginationController;
import com.jack.productsearch.controllers.ProductsController;
import com.jack.productsearch.dao.Dao;
import com.jack.productsearch.models.Category;
import com.jack.productsearch.models.Product;
import com.jack.productsearch.models.ProductState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment implements ProductsCallback, PaginationCallback, RVCallback {

    @BindView(R.id.search_products_recycler_view)
    RecyclerView productsRV;

    @BindView(R.id.search_edit_text)
    EditText searchEditText;

    @BindView(R.id.filters_view)
    View filtersView;

    @BindView(R.id.filter_spinner)
    Spinner filterSpinner;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.search_button)
    Button searchButton;

    @BindView(R.id.progress_bar_pagination)
    ProgressBar progressBarPagination;

    private Dao dao;
    private ProductsController productsController;
    private PaginationController paginationController;
    private ProductsCallback productsCallback;
    private PaginationCallback paginationCallback;
    private RVCallback rvCallback;
    private ProductRVAdapter mAdapter;
    private ProgressDialog progressDialog;
    private static int offset = 0;
    private String searchText;
    private String filterText;

    private View view;

    public SearchFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        dao = new Dao(getActivity());
        productsCallback = this;
        paginationCallback = this;
        rvCallback = this;
        productsController = new ProductsController(productsCallback);
        paginationController = new PaginationController(paginationCallback);

        initUI();
        initSwipeLayout();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((MainActivity) getActivity()).productList != null)
            mAdapter.setItems(((MainActivity) getActivity()).productList);
    }

    private void initUI() {
        List<String> categorNames = new ArrayList<>();

        for (Category category : dao.getCategoryList())
            categorNames.add(category.getName());

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, categorNames);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(filterAdapter);

        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    filtersView.setVisibility(View.VISIBLE);
                } else {
                    filtersView.setVisibility(View.GONE);
                }
            }
        });
        productsRV.setHasFixedSize(true);
        productsRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new ProductRVAdapter(new ArrayList<Product>(), getActivity(),
                ProductState.UNSAVED_PRODUCT, rvCallback);
        productsRV.setAdapter(mAdapter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchEditText.getText().toString();
                filterText = filterSpinner.getSelectedItem().toString();
                closeKeyboard();
                progressDialog = ProgressDialog.show(getActivity(), getString(R.string.please_wait), getString(R.string.searching), true, true);
                productsController.getProducts(filterText, searchText);
            }
        });
        productsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1))
                    swipeRefreshLayout.setEnabled(true);
                else
                    swipeRefreshLayout.setEnabled(false);
            }
        });
    }

    private void initSwipeLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productsController.getProducts(filterText, searchText);
            }
        });
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onProductsLoad(List<Product> productList) {
        searchEditText.clearFocus();
        progressDialog.cancel();
        offset = productList.size();
        filtersView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        mAdapter.setItems(productList);
        mAdapter.notifyDataSetChanged();
        ((MainActivity) getActivity()).productList = productList;
    }

    @Override
    public void onProductAdded(Product product) {
        if (dao.saveProduct(product))
            ((MainActivity) getActivity()).savedProductsAdapter.addItem(product);

    }

    @Override
    public void onProductDeleted(Product product) {
    }

    @Override
    public void onListEnd() {
        progressBarPagination.setVisibility(View.VISIBLE);
        paginationController.getProducts(filterText, searchText, offset);
    }

    @Override
    public void onPaginationProductsLoad(List<Product> productList) {
        progressBarPagination.setVisibility(View.GONE);
        offset = ((MainActivity) getActivity()).productList.size() + productList.size();
        filtersView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        mAdapter.addItems(productList);
        mAdapter.notifyDataSetChanged();
        ((MainActivity) getActivity()).productList.addAll(productList);
    }
}
