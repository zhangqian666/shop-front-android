package com.zack.shop.mvp.ui.activity.set;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;
import com.zack.shop.di.component.DaggerAppSetComponent;
import com.zack.shop.di.module.AppSetModule;
import com.zack.shop.mvp.contract.AppSetContract;
import com.zack.shop.mvp.http.entity.login.UserBean;
import com.zack.shop.mvp.presenter.AppSetPresenter;
import com.zack.shop.mvp.ui.activity.login.LoginActivity;
import com.zack.shop.mvp.utils.AppConstant;
import com.zack.shop.mvp.utils.SpUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

public class AppSetActivity extends BaseSupportActivity<AppSetPresenter> implements AppSetContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.btn_logout)
    Button btnLogout;

    private UserBean userBean;

    @Inject
    AppManager appManager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAppSetComponent.builder().appComponent(appComponent)
                .appSetModule(new AppSetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_app_set;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("个人设置");
        toolbarBack.setVisibility(View.VISIBLE);
        userBean = ((UserBean) getIntent().getSerializableExtra(AppConstant.ActivityIntent.USER_BEAN));
        tvName.setText(userBean.getUsername());
        tvPhone.setText(userBean.getPhone());
        tvSex.setText(userBean.getSex() == 0 ? "男" : "女");
    }


    @OnClick({R.id.btn_logout, R.id.ll_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                SpUtils.put(mContext, AppConstant.Api.TOKEN, "");
                RongIM.getInstance().disconnect();
                startActivity(new Intent(this, LoginActivity.class));
                appManager.killAll(LoginActivity.class);
                break;
            case R.id.ll_name:

                View baseView = LayoutInflater.from(this).inflate(R.layout.dialog_view_edit, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("更改姓名");
                builder.setView(baseView);
                builder.setCancelable(false);
                builder.setPositiveButton("修改", (dialog, which) -> {
                    String text = ((EditText) baseView.findViewById(R.id.et_content)).getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        showMessage("姓名不能为空!");
                        return;
                    }
                    if (mPresenter != null) {
                        mPresenter.updateUserName(text);
                    }
                });
                builder.create().show();
                break;
        }

    }

    @Override
    public void updatePasswordSuccess() {

    }

    @Override
    public void updateUsernameSuccess() {

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
