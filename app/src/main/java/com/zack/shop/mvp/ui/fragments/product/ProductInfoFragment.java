package com.zack.shop.mvp.ui.fragments.product;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportFragment;
import com.zack.shop.mvp.http.entity.product.Product;
import com.zack.shop.mvp.ui.activity.product.ProductDetailsActivity;
import com.zack.shop.mvp.ui.widget.SlideDetailsLayout;
import com.zack.shop.mvp.utils.AppConstant;
import com.zack.shop.mvp.utils.GlideImageLoader;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/6 下午4:21
 * @Package com.zack.shop.mvp.ui.fragments
 **/
public class ProductInfoFragment extends BaseSupportFragment implements SlideDetailsLayout.OnSlideDetailsListener {


    @BindView(R.id.sv_switch)
    SlideDetailsLayout slideDetailsLayout;
    @BindView(R.id.sv_goods_info)
    ScrollView scrollView;
    @BindView(R.id.vp_item_goods_img)
    Banner bannerTitle;
    @BindView(R.id.tv_goods_title)
    TextView productTitle;
    @BindView(R.id.tv_goods_sub_title)
    TextView productSubTitle;
    @BindView(R.id.tv_new_price)
    TextView newPrice;
    @BindView(R.id.tv_user_role)
    TextView tvUserRole;
    @BindView(R.id.tv_comment_count)
    TextView commentCount;
    @BindView(R.id.ll_empty_comment)
    LinearLayout llEmptyComment;
    @BindView(R.id.ll_pull_up)
    LinearLayout llPullUp;
    @BindView(R.id.ll_current_goods)
    LinearLayout llCurrentProduct;
    @BindView(R.id.fab_up_slide)
    FloatingActionButton fabUpSlide;
    private Product productBean;


    public ProductInfoFragment() {

    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_info, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getData();
        initBanner();
        initText();
        slideDetailsLayout.setOnSlideDetailsListener(this);
        initFab();
    }

    private void getData() {
        productBean = ((Product) _mActivity.getIntent().getSerializableExtra(AppConstant.ActivityIntent.BEAN));
    }

    private void initFab() {
        fabUpSlide.hide();
    }

    private void initText() {
        productTitle.setText(productBean.getName());
        productSubTitle.setText(productBean.getSubtitle());
        newPrice.setText(String.format("%s", productBean.getPrice()));
        tvUserRole.setText(String.format("%s", "普通会员"));
        commentCount.setText(String.format("(%s)", "0"));
    }

    private void initBanner() {
        if (!TextUtils.isEmpty(productBean.getSubImages())) {
            String[] split = productBean.getSubImages().split(",");
            List<String> subImages = Arrays.asList(split);
            {
                bannerTitle.setImageLoader(new GlideImageLoader());
//                //设置自动轮播，默认为true
                bannerTitle.isAutoPlay(false);
//                //设置轮播时间
//                bannerTitle.setDelayTime(1500);
                //设置指示器位置（当banner模式中有指示器时）
                bannerTitle.setIndicatorGravity(BannerConfig.CENTER);
                bannerTitle.setImages(subImages);
                bannerTitle.start();
            }
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick({R.id.ll_current_goods, R.id.ll_empty_comment, R.id.ll_pull_up, R.id.sv_switch, R.id.fab_up_slide})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_current_goods:
                break;
            case R.id.ll_empty_comment:
                break;
            case R.id.ll_pull_up:
                //上拉查看图文详情
                slideDetailsLayout.smoothOpen(true);
                break;
            case R.id.sv_switch:
                break;
            case R.id.fab_up_slide:
                //点击滑动到顶部
                scrollView.smoothScrollTo(0, 0);
                slideDetailsLayout.smoothClose(true);
                break;
        }
    }

    @Override
    public void onStatusChanged(SlideDetailsLayout.Status status) {
        ProductDetailsActivity mActivity = (ProductDetailsActivity) _mActivity;
        if (status == SlideDetailsLayout.Status.OPEN) {
            //当前为图文详情页
            fabUpSlide.show();
            mActivity.showTitle(true);
        } else {
            //当前为商品详情页
            fabUpSlide.hide();
            mActivity.showTitle(false);
        }
    }


}
