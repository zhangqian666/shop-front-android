package com.zack.shop.mvp.ui.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.product.Product;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午6:41
 * @Package com.zack.shop.mvp.ui.adapter
 **/


public class RecommendQuickAdapter extends BaseQuickAdapter<Product, BaseViewHolder> {

    public RecommendQuickAdapter(@Nullable List<Product> data) {
        super(R.layout.adapter_item_recommend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Product item) {
        Glide.with(mContext)
                .load(item.getMainImage())
                .into(((ImageView) helper.getView(R.id.iv_product)));
        helper.setText(R.id.tv_product_name, item.getName());
        helper.setText(R.id.tv_product_details, item.getSubtitle());
        helper.setText(R.id.tv_product_price, String.format("%s", item.getPrice()));
    }
}
