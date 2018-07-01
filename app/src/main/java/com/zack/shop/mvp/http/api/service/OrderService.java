package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.order.OrderBean;
import com.zack.shop.mvp.http.entity.order.OrderSettlementsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/15 下午1:56
 * @Package com.zack.shop.mvp.http.api.service
 **/
public interface OrderService {


    @POST("/user/order/list")
    @FormUrlEncoded
    Observable<BaseResponse<List<OrderBean>>> orderList(
            @Field("status") Integer status

    );

    @POST("/user/order/details")
    @FormUrlEncoded
    Observable<BaseResponse> orderDetails(
            @Field("orderNo") Long orderNo
    );

    @POST("/user/order/send_goods")
    @FormUrlEncoded
    Observable<BaseResponse> orderSend(
            @Field("orderNo") Long orderNo
    );


    @POST("/user/order/create")
    @FormUrlEncoded
    Observable<BaseResponse<OrderSettlementsBean>> orderCreate(
            @Field("shippingId") Integer shippingId,
            @Field("productIds") String productIds
    );

    @POST("/user/order/precreate")
    @FormUrlEncoded
    Observable<BaseResponse<OrderSettlementsBean>> orderPreCreate(
            @Field("productIds") String productIds
    );

    @POST("/user/order/cancel")
    @FormUrlEncoded
    Observable<BaseResponse> orderCancel(
            @Field("orderId") Long orderNo
    );

}
