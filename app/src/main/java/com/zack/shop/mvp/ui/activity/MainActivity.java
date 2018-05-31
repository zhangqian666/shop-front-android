package com.zack.shop.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.jess.arms.di.component.AppComponent;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;
import com.zack.shop.di.component.DaggerMainComponent;
import com.zack.shop.di.module.MainModule;
import com.zack.shop.mvp.contract.MainContract;
import com.zack.shop.mvp.http.entity.conversation.TokenBean;
import com.zack.shop.mvp.http.entity.login.UserBean;
import com.zack.shop.mvp.presenter.MainPresenter;
import com.zack.shop.mvp.ui.fragments.CartFragment;
import com.zack.shop.mvp.ui.fragments.CategoryFragment;
import com.zack.shop.mvp.ui.fragments.FindFragment;
import com.zack.shop.mvp.ui.fragments.RecommendFragment;
import com.zack.shop.mvp.ui.fragments.SelfFragment;
import com.zack.shop.mvp.ui.utils.RongIMUtils;
import com.zack.shop.mvp.ui.widget.bottombar.BottomBar;
import com.zack.shop.mvp.ui.widget.bottombar.BottomBarTab;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseSupportActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    private ISupportFragment[] mFragments = new ISupportFragment[5];

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
        initBottomBar();
        if (mPresenter != null) {
            mPresenter.getUserInfo();
            mPresenter.conversationToken();
        }
    }

    private void initBottomBar() {
        addFragment();
        mBottomBar
                .addItem(new BottomBarTab(mContext, R.drawable.icon_navigation_home, "首页"))
                .addItem(new BottomBarTab(mContext, R.drawable.icon_navigation_category, "分类"))
                .addItem(new BottomBarTab(mContext, R.drawable.icon_navigation_cart, "购物车"))
                .addItem(new BottomBarTab(mContext, R.drawable.icon_navigation_find, "发现"))
                .addItem(new BottomBarTab(mContext, R.drawable.icon_navigation_self, "个人"));
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private void addFragment() {
        ISupportFragment recommendFragment = findFragment(RecommendFragment.class);
        if (recommendFragment == null) {
            mFragments[0] = new RecommendFragment();
            mFragments[1] = new CategoryFragment();
            mFragments[2] = new CartFragment();
            mFragments[3] = new FindFragment();
            mFragments[4] = new SelfFragment();
            loadMultipleRootFragment(R.id.fragment_contain, 0, mFragments);
        } else {
            mFragments[0] = findFragment(RecommendFragment.class);
            mFragments[1] = findFragment(CategoryFragment.class);
            mFragments[2] = findFragment(CartFragment.class);
            mFragments[3] = findFragment(FindFragment.class);
            mFragments[4] = findFragment(SelfFragment.class);
        }
    }

    @Override
    public void connectRongIM(TokenBean data) {
        RongIMUtils.connect(mContext, data.getToken());
    }

    @Override
    public void userInfo(UserBean data) {
        RongIM.getInstance().setCurrentUserInfo(new UserInfo(data.getUid().toString(),
                data.getUsername(),
                Uri.parse(data.getImage())));
        RongIM.getInstance().setMessageAttachedUserInfo(true);
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
