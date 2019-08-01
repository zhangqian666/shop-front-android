package com.zack.shop.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jaeger.library.StatusBarUtil;
import com.jess.arms.di.component.AppComponent;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;
import com.zack.shop.di.component.DaggerUserDetailsComponent;
import com.zack.shop.di.module.UserDetailsModule;
import com.zack.shop.mvp.contract.UserDetailsContract;
import com.zack.shop.mvp.http.entity.login.UserBean;
import com.zack.shop.mvp.http.entity.product.Product;
import com.zack.shop.mvp.presenter.UserDetailsPresenter;
import com.zack.shop.mvp.ui.activity.product.ProductDetailsActivity;
import com.zack.shop.mvp.ui.adapter.SearchProductListAdapter;
import com.zack.shop.mvp.utils.AppConstant;

import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;

public class UserDetailsActivity extends BaseSupportActivity<UserDetailsPresenter> implements UserDetailsContract.View {

    @BindView(R.id.btn_send_msg)
    Button btnSendMsg;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerView;
    private UserBean userInfo;
    private SearchProductListAdapter searchProductListAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

        DaggerUserDetailsComponent.builder()
                .appComponent(appComponent)
                .userDetailsModule(new UserDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_details;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(UserDetailsActivity.this, null);
        int userId = getIntent().getIntExtra(AppConstant.User.ID, -1);
        if (mPresenter != null) {
            mPresenter.getUserInfo(userId == -1 ? null : userId);
            mPresenter.getProductList(userId);
        }
        btnSendMsg.setOnClickListener(v -> {
            if (userInfo != null) {
                RongIM.getInstance().startPrivateChat(this, String.valueOf(userInfo.getUid()), userInfo.getUsername());
            }
        });
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (toolbar != null) {
                toolbar.setAlpha(Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        searchProductListAdapter = new SearchProductListAdapter();
        recyclerView.setAdapter(searchProductListAdapter);
        searchProductListAdapter.setEmptyView(LayoutInflater.from(mContext)
                .inflate(R.layout.view_empty, null));

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
    public void userInfoSuccess(UserBean data) {
        this.userInfo = data;

        Glide.with(mContext)
                .load(data.getImage())//new MultiTransformation(
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                .into(ivHeader);
        tvName.setText(data.getUsername());
        switch (data.getRole()) {
            case 0:
                tvRole.setText("超级管理员");
                break;
            case 1:
                tvRole.setText("管理员");
                break;
            case 2:
                tvRole.setText("普通用户");
                break;
        }
        toolbarTitle.setText(data.getUsername());
        tvPhone.setText(data.getPhone());
        tvPhone.setOnClickListener(v -> {
            callPhone(data.getPhone());
        });
    }

    @Override
    public void productList(List<Product> data) {
        searchProductListAdapter.setNewData(data);
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
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
}
