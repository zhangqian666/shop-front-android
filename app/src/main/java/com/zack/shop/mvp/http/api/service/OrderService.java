package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.product.Product;

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


    @POST("/manage/order/list")
    @FormUrlEncoded
    Observable<BaseResponse<List<Product>>> orderList(
            @Field("keyword") String keyword,
            @Field("categoryId") int categoryId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("orderBy") String orderBy
    );

    @POST("/manage/order/details")
    @FormUrlEncoded
    Observable<BaseResponse<List<Product>>> orderDetails(
            @Field("orderNo") Long orderNo
    );

    @POST("/manage/order/send_goods")
    @FormUrlEncoded
    Observable<BaseResponse<List<Product>>> orderSend(
            @Field("orderNo") Long orderNo
    );


    @POST("/user/order/create")
    @FormUrlEncoded
    Observable<BaseResponse<List<Product>>> orderCreate(
            @Field("shippingId") Integer shippingId
    );

    @POST("/manage/order/cancel")
    @FormUrlEncoded
    Observable<BaseResponse<List<Product>>> orderCancel(
            @Field("orderNo") Long orderNo
    );

}
