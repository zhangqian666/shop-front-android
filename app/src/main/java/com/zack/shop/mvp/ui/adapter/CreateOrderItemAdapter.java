package com.zack.shop.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.order.OrderItemBean;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/20 上午11:24
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class CreateOrderItemAdapter extends BaseQuickAdapter<OrderItemBean, BaseViewHolder> {
    public CreateOrderItemAdapter() {
        super(R.layout.adapter_item_create_order_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderItemBean item) {
        helper.setText(R.id.tv_product_title, item.getProductVo().getName());
        helper.setText(R.id.tv_product_subtitle, item.getProductVo().getSubtitle());
        helper.setText(R.id.tv_product_price, String.format("%s", item.getProductVo().getPrice()));
        helper.setText(R.id.tv_count, String.format("* %s", item.getQuantity()));
        Glide.with(mContext).load(item.getProductVo().getMainImage()).into(((ImageView) helper.getView(R.id.iv_product)));
    }
}
