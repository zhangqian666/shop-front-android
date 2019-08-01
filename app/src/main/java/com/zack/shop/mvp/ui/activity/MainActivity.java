package com.zack.shop.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
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
import com.zack.shop.mvp.ui.widget.bottombar.BottomBar;
import com.zack.shop.mvp.ui.widget.bottombar.BottomBarTab;
import com.zack.shop.mvp.utils.AppConstant;
import com.zack.shop.mvp.utils.PicChooserHelper;
import com.zack.shop.mvp.utils.RongIMUtils;
import com.zack.shop.mvp.utils.SpUtils;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import me.yokeyword.fragmentation.ISupportFragment;
import timber.log.Timber;

public class MainActivity extends BaseSupportActivity<MainPresenter> implements MainContract.View, IUnReadMessageObserver {

    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    private ISupportFragment[] mFragments = new ISupportFragment[5];
    private PicChooserHelper picChooserHelper;
    private BottomBarTab homeTab;
    private BottomBarTab categoryTab;
    private BottomBarTab cartTab;
    private BottomBarTab findTab;
    private BottomBarTab selfTab;
    private double firstTime = 0;

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
        setStatusBar();
        initBottomBar();
        initRongIM();
        requestPermissions();
        if (mPresenter != null) {
            mPresenter.getUserInfo();
            mPresenter.conversationToken();
        }
    }

    private void initRongIM() {
        RongIM.getInstance().getUnreadCount(new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                homeTab.setUnreadCount(integer);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        }, Conversation.ConversationType.PRIVATE);
        RongIM.getInstance().addUnReadMessageCountChangedObserver(MainActivity.this, Conversation.ConversationType.PRIVATE);
    }

    @Override
    public void onCountChanged(int i) {
        homeTab.setUnreadCount(i);
    }

    @Override

    protected void onDestroy() {
        super.onDestroy();
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(this);
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
        rxPermission
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS)
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        Timber.e("%s is granted.", permission.name);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        Timber.d("%s is denied. More info should be provided.", permission.name);
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Timber.e("%s is denied.", permission.name);
                    }
                });


    }

    private void initBottomBar() {
        addFragment();
        homeTab = new BottomBarTab(mContext, R.drawable.icon_navigation_home, "首页");
        categoryTab = new BottomBarTab(mContext, R.drawable.icon_navigation_category, "分类");
        cartTab = new BottomBarTab(mContext, R.drawable.icon_navigation_cart, "购物车");
        findTab = new BottomBarTab(mContext, R.drawable.icon_navigation_find, "发现");
        selfTab = new BottomBarTab(mContext, R.drawable.icon_navigation_self, "个人");
        mBottomBar
                .addItem(homeTab)
                .addItem(categoryTab)
                .addItem(cartTab)
                .addItem(findTab)
                .addItem(selfTab);
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
        Timber.i("RongIM token : %s", data.getToken());
        RongIMUtils.connect(mContext, data.getToken());
    }

    @Override
    public void userInfo(UserBean data) {
        SpUtils.put(mContext, AppConstant.User.INFO, data);
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

    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }

    @Override
    public void onBackPressedSupport() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ArmsUtils.snackbarText("再按一次退出程序");
            firstTime = secondTime;
        } else {
            ArmsUtils.exitApp();
        }
    }
}
