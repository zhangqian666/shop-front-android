package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.product.Product;
import com.zack.shop.mvp.http.entity.product.RecommendBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/10 下午3:04
 * @Package com.zack.shop.mvp.http.api.service
 **/
public interface ProductService {

    @GET("/user/product/recommend")
    Observable<BaseResponse<RecommendBean>> getRecommendedProducts(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize,
            @Query("orderBy") String orderBy
    );

    @GET("/user/product/recommend")
    Observable<BaseResponse<Product>> getRecommendedImages(
            @Query("type") int type
    );


    @POST("/user/product/list")
    @FormUrlEncoded
    Observable<BaseResponse<List<Product>>> searchProductByKeyWordOrCategoryId(
            @Field("keyword") String keyword,
            @Field("categoryId") int categoryId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("orderBy") String orderBy
    );



}
