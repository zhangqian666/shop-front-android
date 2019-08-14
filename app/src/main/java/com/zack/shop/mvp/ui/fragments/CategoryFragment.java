package com.zack.shop.mvp.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportFragment;
import com.zack.shop.di.component.DaggerCategoryComponent;
import com.zack.shop.di.module.CategoryModule;
import com.zack.shop.mvp.contract.CategoryContract;
import com.zack.shop.mvp.http.entity.category.CategoryBean;
import com.zack.shop.mvp.presenter.CategoryPresenter;
import com.zack.shop.mvp.ui.activity.product.SearchProductListActivity;
import com.zack.shop.mvp.ui.adapter.CategoryLeftAdapter;
import com.zack.shop.mvp.ui.adapter.CategoryRightAdapter;
import com.zack.shop.mvp.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseSupportFragment<CategoryPresenter> implements CategoryContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.recycler_left)
    RecyclerView recyclerLeft;
    @BindView(R.id.recycler_right)
    RecyclerView recyclerRight;
    @Inject
    CategoryLeftAdapter categoryLeftAdapter;
    @Inject
    CategoryRightAdapter categoryRightAdapter;


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCategoryComponent
                .builder()
                .appComponent(appComponent)
                .categoryModule(new CategoryModule(this))
                .build()
                .inject(this);

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initHeader();
        initLeftRecycler();
        initRightRecycler();
    }

    private void initHeader() {
        toolbarTitle.setText("分类");
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        assert mPresenter != null;
        mPresenter.getCategorys(0);
    }

    private void initRightRecycler() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(_mActivity, 3);
        recyclerRight.setLayoutManager(gridLayoutManager);
        recyclerRight.setAdapter(categoryRightAdapter);
        categoryRightAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty, null));

        categoryRightAdapter.setOnItemClickListener((adapter, view, position) -> {
            CategoryBean categoryBean = (CategoryBean) adapter.getData().get(position);
            Intent intent = new Intent(_mActivity, SearchProductListActivity.class);
            intent.putExtra(AppConstant.ActivityIntent.SEARCH_CONTENT, categoryBean.getName());
            _mActivity.startActivity(intent);
        });
    }

    private void initLeftRecycler() {
        recyclerLeft.setLayoutManager(new LinearLayoutManager(_mActivity));
        categoryLeftAdapter.setOnItemClickListener((adapter, view, position) -> {
            categoryLeftAdapter.setSelectedPosition(position);

            categoryRightAdapter.setNewData(new ArrayList<>());
            if (mPresenter != null) {
                mPresenter.getCategorys(((CategoryLeftAdapter) adapter).getData().get(position).getId());
            }
        });
        recyclerLeft.setAdapter(categoryLeftAdapter);
        categoryLeftAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty, null));
    }

    @Override
    public void getCategoryBeanList(List<CategoryBean> data) {
        categoryLeftAdapter.replaceData(data);
        categoryLeftAdapter.setSelectedPosition(0);
        if (data.size() != 0) {
            if (mPresenter != null) {
                mPresenter.getCategorys(data.get(0).getId());
            }
        }
    }

    @Override
    public void getItemCategoryBeanList(List<CategoryBean> data) {
        categoryRightAdapter.setNewData(data);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }


}
