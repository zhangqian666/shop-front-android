package com.zack.shop.mvp.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportFragment;
import com.zack.shop.di.component.DaggerFindComponent;
import com.zack.shop.di.module.FindModule;
import com.zack.shop.mvp.contract.FindContract;
import com.zack.shop.mvp.http.entity.moment.MomentBean;
import com.zack.shop.mvp.presenter.FindPresenter;
import com.zack.shop.mvp.ui.adapter.FindAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseSupportFragment<FindPresenter> implements FindContract.View, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recycler_list)
    RecyclerView recyclerFindList;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    FindAdapter findAdapter;


    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .findModule(new FindModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        assert mPresenter != null;
        mPresenter.getMoments();
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerFindList.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerFindList.setAdapter(findAdapter);

        findAdapter.setOnHeartClickListener(momentId -> mPresenter.starMoment(momentId));

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void refreshView(List<MomentBean> data) {
        findAdapter.setNewData(data);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
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

    /**
     * swipeRefreshLayout 刷新回调
     */
    @Override
    public void onRefresh() {
        assert mPresenter != null;
        mPresenter.getMoments();
    }
}
