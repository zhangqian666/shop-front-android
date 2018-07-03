package com.zack.shop.mvp.ui.adapter;

import android.view.View;
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
        Glide.with(mContext).load(orderItemBean.getProductImage()).into(((ImageView) helper.getView(R.id.iv_product)));

        helper.setText(R.id.tv_product_title, orderItemBean.getProductVo().getName());
        helper.setText(R.id.tv_product_subtitle, orderItemBean.getProductVo().getSubtitle());
        helper.setText(R.id.tv_product_price, String.format("%s", orderItemBean.getProductVo().getPrice()));
        helper.setText(R.id.tv_count, String.format("* %s", orderItemBean.getQuantity()));

        helper.getView(R.id.tv_pay).setVisibility(View.GONE);
        helper.getView(R.id.tv_cancel_order).setVisibility(View.GONE);
        helper.getView(R.id.tv_friend_pay).setVisibility(View.GONE);
        helper.getView(R.id.tv_delete).setVisibility(View.GONE);


        switch (item.getStatus()) {
            case 0://已取消
                helper.getView(R.id.tv_delete).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, "订单已取消");
                break;
            case 10://未付款
                helper.getView(R.id.tv_pay).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_cancel_order).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_friend_pay).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, "等待买家付款");
                break;
            case 20://已付款
                helper.getView(R.id.tv_delete).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, "等待卖家发货");
                break;
            case 40://已发货
                helper.getView(R.id.tv_delete).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, "等待买家收货");
                break;
        }


        helper.getView(R.id.tv_cancel_order).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick(item);
        });

    }

    public interface OnClickChooseListener {
        void onClick(OrderBean item);
    }

    private OnClickChooseListener onClickChooseListener;

    public void setOnClickChooseListener(OnClickChooseListener onClickChooseListener) {
        this.onClickChooseListener = onClickChooseListener;
    }
}
