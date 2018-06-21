package com.zack.shop.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.order.OrderBean;
import com.zack.shop.mvp.http.entity.order.OrderItemBean;
import com.zack.shop.mvp.http.entity.order.OrderShopBean;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/20 下午2:59
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class OrderManageListAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {
    public OrderManageListAdapter() {
        super(R.layout.adapter_item_order_manager_list);
    }


    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        OrderShopBean orderShopBean = item.getOrderShopVo();
        OrderItemBean orderItemBean = orderShopBean.getOrderItemVos().get(0);
        helper.setText(R.id.tv_shop_name, orderShopBean.getShopName());
        helper.setText(R.id.tv_status, String.format("订单ID: %s", item.getOrderNo()));
        Glide.with(mContext).load(orderItemBean.getProductImage()).into(((ImageView) helper.getView(R.id.iv_product)));

        helper.setText(R.id.tv_product_title, orderItemBean.getProductVo().getName());
        helper.setText(R.id.tv_product_subtitle, orderItemBean.getProductVo().getSubtitle());
        helper.setText(R.id.tv_product_price, String.format("%s", orderItemBean.getProductVo().getPrice()));
        helper.setText(R.id.tv_count, String.format("* %s", orderItemBean.getQuantity()));


    }
}
