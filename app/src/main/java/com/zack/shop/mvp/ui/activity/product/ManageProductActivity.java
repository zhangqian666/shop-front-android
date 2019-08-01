package com.zack.shop.mvp.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.zack.shop.di.component.DaggerManageProductComponent;
import com.zack.shop.di.module.ManageProductModule;
import com.zack.shop.mvp.contract.ManageProductContract;
import com.zack.shop.mvp.http.entity.product.Product;
import com.zack.shop.mvp.presenter.ManageProductPresenter;
import com.zack.shop.mvp.ui.adapter.ManageProductAdapter;
import com.zack.shop.mvp.utils.AppConstant;

import java.util.List;

import butterknife.BindView;

public class ManageProductActivity extends BaseSupportActivity<ManageProductPresenter> implements ManageProductContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private ManageProductAdapter manageProductAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerManageProductComponent.builder()
                .appComponent(appComponent)
                .manageProductModule(new ManageProductModule(this))
                .build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_manage_product;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.list();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("商品管理");
        toolbarBack.setVisibility(View.VISIBLE);
        initRecyclerView();
    }

    private void initRecyclerView() {
        swipeRefresh.setOnRefreshListener(() -> {
            if (mPresenter != null) {
                mPresenter.list();
            }
        });
        manageProductAdapter = new ManageProductAdapter();
        recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerList.setAdapter(manageProductAdapter);

        manageProductAdapter.setOnClickBtnListener(new ManageProductAdapter.OnClickBtnListener() {
            @Override
            public void onClick(Product item, boolean onSale) {
                if (mPresenter != null) {
                    mPresenter.updateStatus(item.getId(), onSale ? 2 : 1);
                }
            }

            @Override
            public void onClickEdit(Product item) {
                Intent intent = new Intent(mContext, CreateProductActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable(AppConstant.ActivityIntent.PRODUCT_BEAN, item);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        manageProductAdapter.setEmptyView(LayoutInflater.from(mContext).inflate(R.layout.view_empty, null));

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

    @Override
    public void productListsuccess(List<Product> data) {
        manageProductAdapter.setNewData(data);
    }

    @Override
    public void updateStatusSuccess() {
        if (mPresenter != null) {
            mPresenter.list();
        }
    }
}
