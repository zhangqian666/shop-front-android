package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.cart.CartBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/7 下午2:21
 * @Package com.zack.shop.mvp.http.api.service
 **/
public interface CartService {

    @GET("/user/cart/list")
    Observable<BaseResponse<Map<Integer, List<CartBean>>>> list(
    );

    @FormUrlEncoded
    @POST("/user/cart/add")
    Observable<BaseResponse> addProduct(
            @Field("productId") Integer productId,
            @Field("count") Integer count
    );

    @FormUrlEncoded
    @POST("/user/cart/delete")
    Observable<BaseResponse> deleteProduct(
            @Field("productIds") String productIds
    );

    @FormUrlEncoded
    @POST("/user/cart/select")
    Observable<BaseResponse<Map<Integer, List<CartBean>>>> selectProduct(
            @Field("productId") Integer productId,
            @Field("checked") Integer checked

    );

    @FormUrlEncoded
    @POST("/user/cart/update/count")
    Observable<BaseResponse<Map<Integer, List<CartBean>>>> updateProductCount(
            @Field("productId") Integer productId,
            @Field("count") Integer count

    );
}
