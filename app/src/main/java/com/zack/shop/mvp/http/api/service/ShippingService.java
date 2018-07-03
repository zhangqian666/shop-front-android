package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.ship.ShippingBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/19 上午10:31
 * @Package com.zack.shop.mvp.http.api.service
 **/
public interface ShippingService {


    @POST("/user/shipping/list")
    Observable<BaseResponse<List<ShippingBean>>> list();

    @FormUrlEncoded
    @POST("/user/shipping/del")
    Observable<BaseResponse> del(
            @Field("shippingId") Integer shippingId
    );

    /**
     * 选择 shippingId 为默认的收货地址
     *
     * @param shippingId
     * @return
     */
    @FormUrlEncoded
    @POST("/user/shipping/select")
    Observable<BaseResponse> select(
            @Field("shippingId") Integer shippingId
    );

    @FormUrlEncoded
    @POST("/user/shipping/update")
    Observable<BaseResponse> update(
            @Field("shippingId") Integer shippingId,
            @Field("type") Integer type,
            @Field("receiverName") String receiverName,
            @Field("receiverPhone") String receiverPhone,
            @Field("receiverMobile") String receiverMobile,
            @Field("receiverProvince") String receiverProvince,
            @Field("receiverCity") String receiverCity,
            @Field("receiverDistrict") String receiverDistrict,
            @Field("receiverAddress") String receiverAddress
    );

    @FormUrlEncoded
    @POST("/user/shipping/add")
    Observable<BaseResponse> add(
            @Field("type") Integer type,
            @Field("receiverName") String receiverName,
            @Field("receiverPhone") String receiverPhone,
            @Field("receiverMobile") String receiverMobile,
            @Field("receiverProvince") String receiverProvince,
            @Field("receiverCity") String receiverCity,
            @Field("receiverDistrict") String receiverDistrict,
            @Field("receiverAddress") String receiverAddress
    );

    @FormUrlEncoded
    @POST("/user/shipping/address")
    Observable<BaseResponse> address(
            @Field("shengCode") String shengCode,
            @Field("diCode") String diCode,
            @Field("level") Integer level
    );


}
