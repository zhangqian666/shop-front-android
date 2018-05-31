package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.category.CategoryBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/13 下午4:58
 * @Package com.zack.shop.mvp.http.api.service
 **/
public interface CategoryService {

    @FormUrlEncoded
    @POST("/manage/product/category/item/list")
    Observable<BaseResponse<List<CategoryBean>>> getCategorys(
            @Field("parentId") int parendId
    );
}
