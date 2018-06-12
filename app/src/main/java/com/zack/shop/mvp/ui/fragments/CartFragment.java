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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportFragment;
import com.zack.shop.di.component.DaggerCartComponent;
import com.zack.shop.di.module.CartModule;
import com.zack.shop.mvp.contract.CartContract;
import com.zack.shop.mvp.http.entity.cart.CartBean;
import com.zack.shop.mvp.presenter.CartPresenter;
import com.zack.shop.mvp.ui.adapter.CartListAdapter;
import com.zack.shop.mvp.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseSupportFragment<CartPresenter> implements CartContract.View, CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @Inject
    CartListAdapter cartListAdapter;
    @BindView(R.id.cb_balance)
    CheckBox cbBalance;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.btn_balance)
    Button btnBalance;

    private boolean isEdite = true;
    private ProgressDialogUtils progressDialogUtils;

    private List<CartBean> cartBeans = new ArrayList<>();


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCartComponent.builder()
                .appComponent(appComponent)
                .cartModule(new CartModule(this))
                .build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar();
        initRecyclerView();
        initBtn();
    }

    private void initToolBar() {
        toolbarTitle.setText("购物车");
        toolbarRight.setText("编辑");
        toolbarRight.setOnClickListener((View v) -> {
            if (toolbarRight.getText().toString().equals("编辑")) {
                toolbarRight.setText("完成");
                changeToComplete(true);
            } else {
                toolbarRight.setText("编辑");
                changeToComplete(false);

            }
        });
    }


    private void initBtn() {
        tvBalance.setText("0");
        cbBalance.setOnCheckedChangeListener(this);
    }

    private void initRecyclerView() {
        swipeRefresh.setOnRefreshListener(() -> mPresenter.list());
        recyclerList.setLayoutManager(new LinearLayoutManager(_mActivity));
//        DividerItemDecoration decor = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
//        decor.setDrawable(getResources().getDrawable(R.drawable.divider_cart_recycler));
//        recyclerList.addItemDecoration(decor);
        recyclerList.setAdapter(cartListAdapter);
        cartListAdapter.setOnClickCartItemListener(new CartListAdapter.OnClickCartItemListener() {
            @Override
            public void onClickAmountCount(CartBean cartBean, int count) {
                if (mPresenter != null) {
                    mPresenter.updateProductCount(cartBean.getProductId(), count);
                }
            }

            @Override
            public void onChecked(CartBean cartBean, boolean isChecked) {
                if (isChecked) {
                    cartBeans.add(cartBean);
                } else {
                    cartBeans.remove(cartBean);
                }
                refreshAllCheckAndCurrentPrice();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.list();
        }
    }

    @OnClick(R.id.btn_balance)
    public void onViewClicked() {
        if (isEdite) {
            if (cartBeans.size() == 0) {
                return;
            }
            //删除当前选中的产品
            {
                StringBuilder productIds = new StringBuilder();
                for (CartBean cb : cartBeans) {
                    productIds.append(cb.getProductId()).append(",");
                }
                productIds.deleteCharAt(productIds.length() - 1);
                if (mPresenter != null) {
                    mPresenter.deleteProduct(productIds.toString());
                }
            }
        } else {
            ArmsUtils.snackbarText("去结算");
        }

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    public void changeToComplete(boolean isEdit) {
        isEdite = isEdit;
        if (isEdit) {
            btnBalance.setText("删除");
        } else {
            btnBalance.setText("去结算");
        }
    }

    @Override
    public void cartList(List<CartBean> data) {
        cartBeans.clear();
        cartListAdapter.setNewData(data);
        refreshAllCheckAndCurrentPrice();
    }

    @Override
    public void deleteProductIdsSuccess() {
        if (mPresenter != null) {
            mPresenter.list();
        }
        toolbarRight.setText("编辑");
        changeToComplete(false);
    }

    @Override
    public void updateProductCount(List<CartBean> baseResponse) {
        cartBeans.clear();
        cartListAdapter.setNewData(baseResponse);
        refreshAllCheckAndCurrentPrice();
    }

    @Override
    public void showLoading() {
        progressDialogUtils = ProgressDialogUtils.getInstance(_mActivity).show();
    }

    @Override
    public void hideLoading() {
        if (progressDialogUtils != null) progressDialogUtils.dismiss();
        if (swipeRefresh != null) {
            swipeRefresh.setRefreshing(false);
        }
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

    public void refreshCurrentPrice() {
        double price = 0;
        for (CartBean cb : cartBeans) {
            price = price + cb.getProductVo().getPrice() * cb.getQuantity();
        }
        tvBalance.setText(String.format("合计: %s", price));
    }

    public void refreshAllCheckAndCurrentPrice() {
        boolean isAllChecked = false;
        if (cartListAdapter.getData().size() != 0 && cartListAdapter.getData().size() == cartBeans.size()) {
            isAllChecked = true;
        }
        cbBalance.setOnCheckedChangeListener(null);
        cbBalance.setChecked(isAllChecked);
        cbBalance.setOnCheckedChangeListener(this);

        refreshCurrentPrice();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        cartListAdapter.setAllChecked(isChecked);
        cartBeans.clear();
        if (isChecked) {
            cartBeans.addAll(cartListAdapter.getData());
        }
        refreshCurrentPrice();
    }
}
