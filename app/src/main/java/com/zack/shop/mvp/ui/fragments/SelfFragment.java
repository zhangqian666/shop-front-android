package com.zack.shop.mvp.ui.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportFragment;
import com.zack.shop.di.component.DaggerSelfComponent;
import com.zack.shop.di.module.SelfModule;
import com.zack.shop.mvp.contract.SelfContract;
import com.zack.shop.mvp.http.entity.login.UserBean;
import com.zack.shop.mvp.presenter.SelfPresenter;
import com.zack.shop.mvp.ui.activity.order.OrderManageListActivity;
import com.zack.shop.mvp.ui.activity.product.CreateProductActivity;
import com.zack.shop.mvp.ui.activity.product.ManageProductActivity;
import com.zack.shop.mvp.ui.activity.set.AppSetActivity;
import com.zack.shop.mvp.utils.AppConstant;
import com.zack.shop.mvp.utils.PicChooserHelper;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zack.shop.mvp.utils.PicChooserHelper.PicType.Avatar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelfFragment extends BaseSupportFragment<SelfPresenter> implements SelfContract.View {


    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_role)
    TextView tvRole;
    private PicChooserHelper picChooserHelper;

    private UserBean userBean;

    public SelfFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSelfComponent.builder()
                .appComponent(appComponent)
                .selfModule(new SelfModule(this))
                .build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_self, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.getUserInfo();
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick({R.id.iv_header, R.id.iv_set, R.id.ll_header, R.id.ll_wait_pay, R.id.ll_wait_send, R.id.ll_wait_receive, R.id.ll_pay_after, R.id.ll_create_product, R.id.ll_manage_product, R.id.tv_see_more_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                uploadImage();
                break;
            case R.id.iv_set:
                if (userBean != null) {
                    Intent intent = new Intent(_mActivity, AppSetActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable(AppConstant.ActivityIntent.USER_BEAN,
                            userBean);
                    intent.putExtras(extras);
                    _mActivity.startActivity(intent);
                }
                break;
            case R.id.ll_header:
                break;
            case R.id.ll_wait_pay:
                _mActivity.startActivity(new Intent(_mActivity, OrderManageListActivity.class));
                break;
            case R.id.ll_wait_send:
                _mActivity.startActivity(new Intent(_mActivity, OrderManageListActivity.class));
                break;
            case R.id.ll_wait_receive:
                _mActivity.startActivity(new Intent(_mActivity, OrderManageListActivity.class));
                break;
            case R.id.tv_see_more_order:
                _mActivity.startActivity(new Intent(_mActivity, OrderManageListActivity.class));
                break;
            case R.id.ll_pay_after:
                ArmsUtils.snackbarText("售后");
                break;
            case R.id.ll_create_product:
                _mActivity.startActivity(new Intent(_mActivity, CreateProductActivity.class));
                break;
            case R.id.ll_manage_product:
                _mActivity.startActivity(new Intent(_mActivity, ManageProductActivity.class));
                break;

        }
    }

    public void uploadImage() {
        picChooserHelper = new PicChooserHelper(this, Avatar);
        picChooserHelper.setOnChooseResultListener(url -> {
            if (mPresenter != null) {
                mPresenter.updateUserImage(url);
            }
        });

        final String items[] = {"相机", "图库"};
        AlertDialog.Builder builder = new AlertDialog.Builder(_mActivity, 3);
        builder.setTitle("头像上传");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    picChooserHelper.takePicFromCamera();
                    break;
                case 1:
                    picChooserHelper.takePicFromAlbum();
                    break;
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (picChooserHelper != null)
            picChooserHelper.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void getUserInfo(UserBean data) {
        Glide.with(_mActivity)
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
        this.userBean = data;
    }

    @Override
    public void updateUserImageSuccess() {
        if (mPresenter != null) {
            mPresenter.getUserInfo();
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
}
