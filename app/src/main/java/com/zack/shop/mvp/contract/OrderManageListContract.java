package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.order.OrderBean;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/20 下午2:36
 * @Package com.zack.shop.mvp.contract
 **/
public interface OrderManageListContract {
    interface View extends IView {

        void orderList(List<OrderBean> data);

        void cancelOrderSuccess(Object data);

    }

    interface Model extends IModel {

    }
}
