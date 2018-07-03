package com.zack.shop.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.product.Product;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/21 下午5:55
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class SearchProductListAdapter extends BaseQuickAdapter<Product, BaseViewHolder> {

    public SearchProductListAdapter() {
        super(R.layout.adapter_item_search_product_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Product item) {
        helper.setText(R.id.tv_product_title, item.getName());
        helper.setText(R.id.tv_product_subtitle, item.getSubtitle());
        helper.setText(R.id.tv_product_price, String.format("%s", item.getPrice()));
        Glide.with(mContext).load(item.getMainImage()).into(((ImageView) helper.getView(R.id.iv_product)));
    }
}
