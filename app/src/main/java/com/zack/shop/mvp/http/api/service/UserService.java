package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/10 下午4:41
 * @Package com.zack.shop.mvp.http.api.service
 **/
public interface UserService {

    @GET("/user/info")
    Observable<BaseResponse<UserBean>> userDetails();


    @Multipart
    @POST(value = "/user/update/image")
    Observable<BaseResponse<String>> updateUserImage(
            @Part("upload_file") MultipartBody.Part upload_file
    );


}
