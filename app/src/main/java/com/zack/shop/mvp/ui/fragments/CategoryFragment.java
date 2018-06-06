package com.zack.shop.mvp.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportFragment;
import com.zack.shop.di.component.DaggerCategoryComponent;
import com.zack.shop.di.module.CategoryModule;
import com.zack.shop.mvp.contract.CategoryContract;
import com.zack.shop.mvp.http.entity.category.CategoryBean;
import com.zack.shop.mvp.presenter.CategoryPresenter;
import com.zack.shop.mvp.ui.adapter.CategoryLeftAdapter;
import com.zack.shop.mvp.ui.adapter.CategoryRightAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseSupportFragment<CategoryPresenter> implements CategoryContract.View {


    @BindView(R.id.recycler_left)
    RecyclerView recyclerLeft;
    @BindView(R.id.recycler_right)
    RecyclerView recyclerRight;
    @Inject
    CategoryLeftAdapter categoryLeftAdapter;
    @Inject
    CategoryRightAdapter categoryRightAdapter;
    @Inject
    List<CategoryBean> categoryBeans;

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
        initLeftRecycler();
        initRightRecycler();
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
        categoryRightAdapter.setSpanSizeLookup((gridLayoutManager1, position) -> {
            if (categoryBeans.get(position).getItemType() == CategoryBean.HEADER) {
                return 3;
            } else {
                return 1;
            }
        });
        recyclerRight.setAdapter(categoryRightAdapter);
    }

    private void initLeftRecycler() {
        recyclerLeft.setLayoutManager(new LinearLayoutManager(_mActivity));
        categoryLeftAdapter.setOnItemClickListener((adapter, view, position) -> {
            categoryLeftAdapter.setSelectedPosition(position);
            mPresenter.getCategorys(((CategoryLeftAdapter) adapter).getData().get(position).getId());
        });
        recyclerLeft.setAdapter(categoryLeftAdapter);

    }

    @Override
    public void getCategoryBeanList(List<CategoryBean> data) {
        categoryLeftAdapter.replaceData(data);
        categoryLeftAdapter.setSelectedPosition(0);
        if (data.size() != 0) {
            mPresenter.getCategorys(data.get(0).getId());
        }
    }

    @Override
    public void getItemCategoryBeanList(List<CategoryBean> data) {
        categoryBeans.clear();
        categoryBeans.addAll(data);
        categoryRightAdapter.notifyDataSetChanged();
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
