package com.zack.shop.mvp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.zack.shop.di.component.DaggerCreateOrderComponent;
import com.zack.shop.di.module.CreateOrderModule;
import com.zack.shop.mvp.contract.CreateOrderContract;
import com.zack.shop.mvp.http.entity.order.OrderBean;
import com.zack.shop.mvp.http.entity.order.OrderSettlementsBean;
import com.zack.shop.mvp.http.entity.ship.ShippingBean;
import com.zack.shop.mvp.presenter.CreateOrderPresenter;
import com.zack.shop.mvp.ui.adapter.CreateOrderAdapter;
import com.zack.shop.mvp.utils.AppConstant;
import com.zack.shop.mvp.utils.ProgressDialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateOrderActivity extends BaseSupportActivity<CreateOrderPresenter> implements CreateOrderContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    private CreateOrderAdapter createOrderAdapter;
    private View headerAddressView;
    private ShippingBean shippingBean;
    private String productIds;
    private ProgressDialogUtils progressDialogUtils;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCreateOrderComponent.builder()
                .appComponent(appComponent)
                .createOrderModule(new CreateOrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_create_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("生成订单");
        toolbarBack.setVisibility(View.VISIBLE);
        initRecyclerList();
        productIds = getIntent().getStringExtra(AppConstant.ActivityIntent.PRODUCT_IDS);
        if (mPresenter != null && productIds != null) {
            mPresenter.createPreOrder(productIds);
        }

    }

    private void initRecyclerList() {
        recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        createOrderAdapter = new CreateOrderAdapter();
        recyclerList.setAdapter(createOrderAdapter);

        headerAddressView = LayoutInflater.from(mContext).inflate(R.layout.header_activity_create_order, null);
        createOrderAdapter.setHeaderView(headerAddressView);
    }

    private void refreshListHeaderView(ShippingBean shipping) {
        this.shippingBean = shipping;
        TextView receiverName = headerAddressView.findViewById(R.id.receiver_name);
        TextView receiverPhone = headerAddressView.findViewById(R.id.receiver_phone);
        TextView receiverDetails = headerAddressView.findViewById(R.id.receiver_details);

        receiverName.setText(String.format("收货人 : %s", shipping.getReceiverName()));
        receiverPhone.setText(shipping.getReceiverPhone());
        receiverDetails.setText(String.format("详细地址 :%s %s %s",
                shipping.getReceiverProvince(), shipping.getReceiverCity(), shipping.getReceiverDistrict()));
    }


    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        if (shippingBean != null) {
            if (mPresenter != null) {
                mPresenter.createOrder(shippingBean.getId(), productIds);
            }
        } else {
            // TODO: 2018/6/21 临时方法
            if (mPresenter != null) {
                mPresenter.createOrder(null, productIds);
            }
        }
    }

    @Override
    public void showLoading() {
        progressDialogUtils = ProgressDialogUtils.getInstance(mContext).show();
    }

    @Override
    public void hideLoading() {
        if (progressDialogUtils != null) progressDialogUtils.dismiss();
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

    @Override
    public void createOrderSuccess(OrderSettlementsBean data) {
        ArmsUtils.snackbarText("创建成功 : " + data);
        finish();
    }

    @Override
    public void createPreOrderSuccess(OrderSettlementsBean data) {

        tvProductPrice.setText(String.format("%s", data.getSettlementmoney()));
        List<OrderBean> orderBeans = data.getOrderVos();
        createOrderAdapter.setNewData(orderBeans);
    }
}
