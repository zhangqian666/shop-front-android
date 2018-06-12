package com.zack.shop.mvp.ui.activity.set;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;
import com.zack.shop.mvp.http.entity.login.UserBean;
import com.zack.shop.mvp.utils.AppConstant;
import com.zack.shop.mvp.utils.SpUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

public class AppSetActivity extends BaseSupportActivity {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    private UserBean userBean;

    @Inject
    AppManager appManager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

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
        tvSex.setText(userBean.getSex() == 0 ? "男" : "女");
    }


    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        SpUtils.put(mContext, AppConstant.Api.TOKEN, "");
        RongIM.getInstance().disconnect();
        appManager.appExit();
    }
}
