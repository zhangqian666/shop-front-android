package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.order.OrderSettlementsBean;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/19 下午7:13
 * @Package com.zack.shop.mvp.contract
 **/
public interface CreateOrderContract {
    interface View extends IView {

        void createOrderSuccess(OrderSettlementsBean data);

        void createPreOrderSuccess(OrderSettlementsBean data);
    }

    interface Model extends IModel {

    }
}
