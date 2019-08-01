package com.zack.shop.mvp.ui.activity.login;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;
import com.zack.shop.di.component.DaggerLoginComponent;
import com.zack.shop.di.module.LoginModule;
import com.zack.shop.mvp.contract.LoginContract;
import com.zack.shop.mvp.http.entity.login.JWTBean;
import com.zack.shop.mvp.http.entity.login.UserBean;
import com.zack.shop.mvp.presenter.LoginPresenter;
import com.zack.shop.mvp.ui.activity.MainActivity;
import com.zack.shop.mvp.utils.AppConstant;
import com.zack.shop.mvp.utils.ProgressDialogUtils;
import com.zack.shop.mvp.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseSupportActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.phone)
    EditText mPhoneView;

    @BindView(R.id.password)
    EditText mPasswordView;

    @BindView(R.id.btn_login_forget)
    TextView btnLoginForget;

    private ProgressDialogUtils progressDialogUtils;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                attemptLogin();
                break;
            case R.id.btn_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
        }
    }

    private void attemptLogin() {
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            showMessage("密码不能为空/不可用");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showMessage("账号不能为空");
            return;
        }

        showProgress(true);
        if (mPresenter != null) mPresenter.login(phone, password);

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void showProgress(final boolean show) {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(mContext);
            progressDialogUtils.setMessage("登录中");
        } else {
            if (show) {
                progressDialogUtils.show();
            } else {
                progressDialogUtils.dismiss();
            }
        }


    }

    @Override
    public void loginResult(JWTBean msg) {
        SpUtils.put(mContext, AppConstant.Api.TOKEN, msg.getAccess_token());
        Timber.e((String) SpUtils.get(mContext, AppConstant.Api.TOKEN, ""));
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void registerSuccess(UserBean userBeanBaseResponse) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        showProgress(false);
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

