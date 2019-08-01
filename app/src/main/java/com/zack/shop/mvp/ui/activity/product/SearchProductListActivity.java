package com.zack.shop.mvp.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;
import com.zack.shop.di.component.DaggerSearchProductComponent;
import com.zack.shop.di.module.SearchProductModule;
import com.zack.shop.mvp.contract.SearchProductContract;
import com.zack.shop.mvp.http.entity.product.Product;
import com.zack.shop.mvp.presenter.SearchProductPresenter;
import com.zack.shop.mvp.ui.adapter.SearchProductListAdapter;
import com.zack.shop.mvp.utils.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchProductListActivity extends BaseSupportActivity<SearchProductPresenter> implements SearchProductContract.View {
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    private String searchContent;
    private SearchProductListAdapter searchProductListAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchProductComponent.builder()
                .appComponent(appComponent)
                .searchProductModule(new SearchProductModule(this))
                .build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search_product_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        searchContent = getIntent().getStringExtra(AppConstant.ActivityIntent.SEARCH_CONTENT);
        if (mPresenter != null) {
            mPresenter.searchProduct(searchContent, null, "desc");
        }
        tvSearch.setText(searchContent);
        initRecyclerList();
    }

    private void initRecyclerList() {
        recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        searchProductListAdapter = new SearchProductListAdapter();
        recyclerList.setAdapter(searchProductListAdapter);
        searchProductListAdapter.setEmptyView(
                LayoutInflater.from(mContext).inflate(R.layout.view_empty, null)
        );
        searchProductListAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(mContext, ProductDetailsActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable(AppConstant.ActivityIntent.BEAN,
                    ((Product) (adapter.getData()).get(position)));
            intent.putExtras(extras);
            startActivity(intent);
        });
    }

    @Override
    public void productListSuccess(List<Product> data) {
        searchProductListAdapter.setNewData(data);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }


    @OnClick({R.id.toolbar_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.tv_search:
                startActivity(new Intent(mContext, SearchActivity.class));
                finish();
                break;
        }
    }
}
