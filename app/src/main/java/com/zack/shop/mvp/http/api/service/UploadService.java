package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/11 上午10:15
 * @Package com.zack.shop.mvp.http.api.service
 **/
public interface UploadService {

    @Multipart
    @POST("/manage/product/upload/image")
    Observable<BaseResponse<String>> uploadImage(
            @Part MultipartBody.Part upload_file
    );

    @Multipart
    @POST("/manage/product/upload/images")
    Observable<BaseResponse<List<String>>> uploadImages(
            @Part List<MultipartBody.Part> upload_file
    );
}
