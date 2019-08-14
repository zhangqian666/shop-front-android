package com.zack.shop.mvp.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.jess.arms.di.component.AppComponent;
import com.zack.shop.app.base.BaseSupportActivity;
import com.zack.shop.mvp.ui.activity.MainActivity;
import com.zack.shop.mvp.utils.AppConstant;
import com.zack.shop.mvp.utils.SpUtils;

public class SplashActivity extends BaseSupportActivity {


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 判断是否是第一次开启应用
        boolean isFirstOpen = (boolean) SpUtils.get(this, AppConstant.FIRST_OPEN, false);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
            return;
        }

        // 如果不是第一次启动app，则正常显示启动屏
        new Handler().postDelayed(this::enterHomeActivity, 1000);
    }

    private void enterHomeActivity() {
        String token = (String) SpUtils.get(mContext, AppConstant.Api.TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
            finish();
        }
    }
}
