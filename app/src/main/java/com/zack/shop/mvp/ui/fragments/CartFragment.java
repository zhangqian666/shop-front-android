package com.zack.shop.mvp.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.zack.shop.mvp.http.entity.cart.StoreBean;
import com.zack.shop.mvp.presenter.CartPresenter;
import com.zack.shop.mvp.ui.activity.order.CreateOrderActivity;
import com.zack.shop.mvp.ui.adapter.CartListAdapter;
import com.zack.shop.mvp.ui.widget.AmountView;
import com.zack.shop.mvp.utils.AppConstant;
import com.zack.shop.mvp.utils.ProgressDialogUtils;

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

    private boolean isEdite = false;
    private ProgressDialogUtils progressDialogUtils;


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
        recyclerList.setAdapter(cartListAdapter);
        cartListAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty, null));
        cartListAdapter.setOnClickStoreItemListener(new CartListAdapter.OnClickStoreItemListener() {
            @Override
            public void onChecked(boolean isAllChecked) {
                refreshCurrentPrice();
                cbBalance.setOnCheckedChangeListener(null);
                cbBalance.setChecked(isAllChecked);
                cbBalance.setOnCheckedChangeListener(CartFragment.this);
            }

            @Override
            public void onClickAmountCount(View view, CartBean cartBean, int count) {
                if (mPresenter != null) {
                    mPresenter.updateProductCount((AmountView) view, cartBean, count);
                }
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
        List<CartBean> allCheckedCartBean = cartListAdapter.getAllCheckedCartBean();
        if (allCheckedCartBean.size() == 0) {
            showMessage("选中商品不能为空");
            return;
        }

        //拼接字符串
        StringBuilder productIds = new StringBuilder();
        for (CartBean cb : allCheckedCartBean) {
            productIds.append(cb.getProductId()).append(",");
        }
        productIds.deleteCharAt(productIds.length() - 1);

        if (isEdite) {
            //删除当前选中的产品
            if (mPresenter != null) {
                mPresenter.deleteProduct(productIds.toString());
            }
            changeToComplete(false);
        } else {
            //去生成订单
            Intent intent = new Intent(_mActivity, CreateOrderActivity.class);
            intent.putExtra(AppConstant.ActivityIntent.PRODUCT_IDS, productIds.toString());
            startActivity(intent);
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
        for (StoreBean sb : cartListAdapter.getData()) {
            for (CartBean cb : sb.getCartVos()) {
                if (cb.isChecked())
                    price = price + cb.getProductVo().getPrice() * cb.getQuantity();
            }
        }
        tvBalance.setText(String.format("合计: %s", price));
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        cartListAdapter.setAllChecked(isChecked);
        refreshCurrentPrice();
    }

    @Override
    public void cartList(List<StoreBean> data) {

        cartListAdapter.setNewData(data);
        cartListAdapter.setAllChecked(false);
        refreshCurrentPrice();

        cbBalance.setOnCheckedChangeListener(null);
        cbBalance.setChecked(false);
        cbBalance.setOnCheckedChangeListener(this);
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
    public void updateProductCount(CartBean baseResponse) {

    }
}
