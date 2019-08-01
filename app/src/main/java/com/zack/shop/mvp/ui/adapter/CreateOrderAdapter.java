package com.zack.shop.mvp.ui.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.order.OrderBean;
import com.zack.shop.mvp.http.entity.order.OrderItemBean;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/19 下午9:45
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class CreateOrderAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {
    public CreateOrderAdapter() {
        super(R.layout.adapter_item_create_order);
    }


    private void initRecyclerView(BaseViewHolder helper, List<OrderItemBean> orderItemVos) {
        RecyclerView recyclerList = helper.getView(R.id.recycler_list);
        if (recyclerList.getLayoutManager() == null)
            recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        CreateOrderItemAdapter createOrderItemAdapter = new CreateOrderItemAdapter();
        recyclerList.setAdapter(createOrderItemAdapter);
        createOrderItemAdapter.setNewData(orderItemVos);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        helper.setText(R.id.tv_shop_name, item.getOrderShopVo().getShopName());
        initRecyclerView(helper, item.getOrderShopVo().getOrderItemVos());
    }
}
