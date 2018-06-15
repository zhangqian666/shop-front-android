package com.zack.shop.mvp.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.zack.shop.mvp.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class RegisterActivity extends BaseSupportActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.send_sms)
    TextView sendSms;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_register)
    Button btnRegister;

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
        return R.layout.activity_register;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("注册");
        toolbarBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginResult(JWTBean msg) {
        SpUtils.put(mContext, AppConstant.Api.TOKEN, msg.getAccess_token());
        Timber.e((String) SpUtils.get(mContext, AppConstant.Api.TOKEN, ""));
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void registerSuccess(UserBean userBean) {
        String phones = phone.getText().toString().trim();
        String passwords = password.getText().toString().trim();
        if (mPresenter != null) {
            mPresenter.login(phones, passwords);
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

    @OnClick({R.id.send_sms, R.id.btn_register})
    public void onViewClicked(View view) {
        String phones = phone.getText().toString().trim();
        String passwords = password.getText().toString().trim();
        String codes = code.getText().toString().trim();

        switch (view.getId()) {
            case R.id.send_sms:
                if (canUsePhone(phones))
                    if (mPresenter != null) {
                        mPresenter.sendSms(phones);
                    }
                break;
            case R.id.btn_register:
                if (canUsePhone(phones) && canUseCode(codes) && canUsePassword(passwords))
                    if (mPresenter != null) {
                        mPresenter.register(passwords, phones, codes);
                    }
                break;
        }
    }

    public boolean canUsePhone(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            showMessage("手机号不能为空/位数不对");
            return false;
        }
        return true;
    }

    public boolean canUsePassword(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 4) {
            showMessage("密码不能为空/不可用");
            return false;
        }
        return true;
    }

    public boolean canUseCode(String code) {
        if (TextUtils.isEmpty(code) || code.length() < 4) {
            showMessage("验证码不能为空/不可用");
            return false;
        }
        return true;
    }
}
