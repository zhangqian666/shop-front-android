package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.cart.CartBean;
import com.zack.shop.mvp.http.entity.cart.StoreBean;

import java.util.List;

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
    Observable<BaseResponse<List<StoreBean>>> list(
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
            @Field("PRODUCT_IDS") String productIds
    );

    @FormUrlEncoded
    @POST("/user/cart/select")
    Observable<BaseResponse<List<StoreBean>>> selectProduct(
            @Field("productId") Integer productId,
            @Field("checked") Integer checked

    );

    @FormUrlEncoded
    @POST("/user/cart/update/count")
    Observable<BaseResponse<CartBean>> updateProductCount(
            @Field("productId") Integer productId,
            @Field("count") Integer count

    );
}
