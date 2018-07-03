package com.zack.shop.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zack.shop.mvp.http.api.service.OrderService;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.order.OrderBean;
import com.zack.shop.mvp.http.entity.order.OrderSettlementsBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/19 下午7:15
 * @Package com.zack.shop.mvp.model
 **/
public class OrderModel extends BaseModel {

    public OrderModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<List<OrderBean>>> orderList(
            Integer status) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .orderList(status);
    }


    public Observable<BaseResponse> orderDetails(
            Long orderNo) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .orderDetails(orderNo);
    }

    public Observable<BaseResponse> orderSend(
            Long orderNo) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .orderSend(orderNo);
    }

    public Observable<BaseResponse<OrderSettlementsBean>> orderCreate(
            Integer shippingId,
            String productIds) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .orderCreate(shippingId, productIds);
    }

    public Observable<BaseResponse<OrderSettlementsBean>> orderPreCreate(
            String productIds) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .orderPreCreate( productIds);
    }

    public Observable<BaseResponse> orderCancel(
            Long orderNo) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .orderCancel(orderNo);
    }


}
