package com.zack.shop.mvp.ui.activity.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;
import com.zack.shop.mvp.http.entity.product.RecommendBean;
import com.zack.shop.mvp.ui.adapter.ItemTitlePagerAdapter;
import com.zack.shop.mvp.ui.fragments.ProductDetailsFragment;
import com.zack.shop.mvp.ui.fragments.ProductInfoFragment;
import com.zack.shop.mvp.ui.widget.NoScrollViewPager;
import com.zack.shop.mvp.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

public class ProductDetailsActivity extends BaseSupportActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tab_title)
    TabLayout tabLayoutTitle;
    @BindView(R.id.vp_content)
    NoScrollViewPager noScrollViewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private List<Fragment> baseSupportFragments = new ArrayList<>();
    private String[] titles = {"产品", "详情"};
    private RecommendBean.RecommendProductsBean productBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_product_details;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getData();
        initTablayoutAndViewPager();
    }

    private void initTablayoutAndViewPager() {
        tabLayoutTitle.addTab(tabLayoutTitle.newTab().setText(titles[0]));
        tabLayoutTitle.addTab(tabLayoutTitle.newTab().setText(titles[1]));
        tabLayoutTitle.getTabAt(0).select();
        baseSupportFragments.add(new ProductInfoFragment());
        baseSupportFragments.add(new ProductDetailsFragment());

        noScrollViewPager.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                baseSupportFragments, titles));
        tabLayoutTitle.setupWithViewPager(noScrollViewPager, true);
    }

    public void showTitle(boolean isshow) {
        if (isshow) {
            noScrollViewPager.setNoScroll(true);
            tvTitle.setVisibility(View.VISIBLE);
            tabLayoutTitle.setVisibility(View.GONE);
        } else {
            noScrollViewPager.setNoScroll(false);
            tvTitle.setVisibility(View.GONE);
            tabLayoutTitle.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.ll_connect_sale, R.id.ll_connect_cart, R.id.btn_add_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_connect_sale:
                RongIM.getInstance().startPrivateChat(mContext, String.valueOf(productBean.getUserId()), "临时会话");
                break;
            case R.id.ll_connect_cart:
                ArmsUtils.snackbarText("购物车");
                break;
            case R.id.btn_add_cart:
                ArmsUtils.snackbarText("加入购物车");
                break;
        }
    }

    public void getData() {
        productBean = ((RecommendBean.RecommendProductsBean) getIntent().getSerializableExtra(AppConstant.ActivityIntent.Bean));
    }
}
