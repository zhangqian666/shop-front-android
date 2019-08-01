package com.zack.shop.mvp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;
import com.zack.shop.di.component.DaggerOrderManagerListComponent;
import com.zack.shop.di.module.OrderManagerListModule;
import com.zack.shop.mvp.contract.OrderManageListContract;
import com.zack.shop.mvp.http.entity.order.OrderBean;
import com.zack.shop.mvp.presenter.OrderManagePresenter;
import com.zack.shop.mvp.ui.adapter.OrderManageListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderManageListActivity extends BaseSupportActivity<OrderManagePresenter> implements OrderManageListContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private OrderManageListAdapter orderManageListAdapter;

    private Integer currentPage = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderManagerListComponent.builder()
                .appComponent(appComponent)
                .orderManagerListModule(new OrderManagerListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_manage_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("订单管理");
        toolbarBack.setVisibility(View.VISIBLE);
        initTabLayout();
        initSwipeAndRecycler();
    }

    private void initTabLayout() {
        //订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭
        tabLayout.addTab(tabLayout.newTab().setText("全部").setTag(0));
        tabLayout.addTab(tabLayout.newTab().setText("未付款").setTag(10));
        tabLayout.addTab(tabLayout.newTab().setText("已付款").setTag(20));
        tabLayout.addTab(tabLayout.newTab().setText("已发货").setTag(40));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Integer tag = (Integer) tab.getTag();
                if (tag != null) {
                    orderManageListAdapter.setNewData(new ArrayList<>());
                    currentPage = tag;
                    if (tag == 0) {
                        if (mPresenter != null) {
                            mPresenter.orderList(null);
                        }
                    } else {
                        if (mPresenter != null) {
                            mPresenter.orderList(tag);
                        }
                    }

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (mPresenter != null) {
            mPresenter.orderList(null);
        }
    }

    private void initSwipeAndRecycler() {
        swipeRefresh.setOnRefreshListener(() -> {
            if (mPresenter != null) {
                mPresenter.orderList(currentPage == 0 ? null : currentPage);
            }
        });
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        orderManageListAdapter = new OrderManageListAdapter();
        recyclerList.setAdapter(orderManageListAdapter);
        orderManageListAdapter.setEmptyView(
                LayoutInflater.from(mContext).inflate(R.layout.view_empty, null)
        );
        orderManageListAdapter.setOnClickChooseListener(item -> {
            if (mPresenter != null) {
                mPresenter.cancelOrder(item.getOrderNo());
            }
        });
    }

    @Override
    public void orderList(List<OrderBean> data) {
        orderManageListAdapter.setNewData(data);
    }

    @Override
    public void cancelOrderSuccess(Object data) {
        if (mPresenter != null) {
            mPresenter.orderList(currentPage == 0 ? null : currentPage);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
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
