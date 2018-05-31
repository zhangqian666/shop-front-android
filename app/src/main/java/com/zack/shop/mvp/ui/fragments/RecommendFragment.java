package com.zack.shop.mvp.ui.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.LogUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportFragment;
import com.zack.shop.di.component.DaggerRecommendComponent;
import com.zack.shop.di.module.RecommendModule;
import com.zack.shop.mvp.contract.RecommendContract;
import com.zack.shop.mvp.http.entity.product.RecommendBean;
import com.zack.shop.mvp.presenter.RecommendPresenter;
import com.zack.shop.mvp.ui.adapter.RecommendQuickAdapter;
import com.zack.shop.mvp.ui.utils.GlideImageLoader;
import com.zack.shop.mvp.ui.utils.SpUtils;
import com.zack.shop.mvp.ui.widget.RecommendItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends BaseSupportFragment<RecommendPresenter> implements RecommendContract.View {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.banner_header)
    Banner banner;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.iv_conversation)
    ImageView ivConversation;

    @Inject
    List<RecommendBean.RecommendProductsBean> recommendProductsBeans;

    @Inject
    RecommendQuickAdapter recommendQuickAdapter;

    public RecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRecommendComponent
                .builder()
                .appComponent(appComponent)
                .recommendModule(new RecommendModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        assert mPresenter != null;
        mPresenter.getRecommend();
        initRecycler();
        initBanner();
        initAppLayout();
    }


    private void initAppLayout() {
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset >= 0) {
                swipe_refresh.setEnabled(true);
            } else {
                swipe_refresh.setEnabled(false);
            }
            mToolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.red), Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));
        });
        Map<String, Boolean> supportedConversation = new HashMap<>();
        supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false);
        ivConversation.setOnClickListener(v ->
                RongIM.getInstance().startConversationList(_mActivity, supportedConversation)
        );
    }

    private void initRecycler() {
        LogUtils.debugInfo("initRecycler");
        swipe_refresh.setOnRefreshListener(() -> mPresenter.getRecommend());
        swipe_refresh.setProgressViewOffset(true, 130, 300);
        swipe_refresh.setColorSchemeColors(getResources().getColor(R.color.red));
        mRecyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 2));
        mRecyclerView.addItemDecoration(new RecommendItemDecoration(10));
        mRecyclerView.setAdapter(recommendQuickAdapter);

        recommendQuickAdapter.setOnItemClickListener((adapter, view, position) -> {


            RongIM.getInstance().startPrivateChat(_mActivity, "0", "小王子");
        });
    }

    private void initBanner() {
        banner.setImageLoader(new GlideImageLoader());
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImages(new ArrayList<>());
        banner.start();
    }

    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void refreshBannerAndList(RecommendBean data) {
        if (swipe_refresh != null && swipe_refresh.isRefreshing())
            swipe_refresh.setRefreshing(false);
        recommendProductsBeans.clear();
        recommendProductsBeans.addAll(data.getRecommendProducts());
        recommendQuickAdapter.notifyDataSetChanged();
        banner.update(data.getRecommendImages());

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
