package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.login.JWTBean;
import com.zack.shop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/10 下午2:28
 * @Package com.zack.shop.mvp.http.api.service
 **/
public interface LoginService {

    @GET("/code/sms")
    Observable<BaseResponse<String>> sendSms(
            @Query("phone") String mobile);

    @FormUrlEncoded
    @POST("/user/register")
    Observable<BaseResponse<UserBean>> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("smsCode") String smsCode,
            @Field("providerId") String providerId,
            @Field("openId") String openId
    );

    @FormUrlEncoded
    @POST("/login")
    Observable<BaseResponse<JWTBean>> loginByUsernamePassword(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/login/mobile")
    Observable<BaseResponse<JWTBean>> loginBySMS(
            @Field("username") String username,
            @Field("code") int code
    );

    /**
     * @param providerId qq or wexin
     * @return
     */
    @FormUrlEncoded
    @POST("/login/openid")
    Observable<BaseResponse<JWTBean>> loginByOpenId(
            @Field("providerId") String providerId,
            @Field("providerUserId") int providerUserId,
            @Field("displayName") String displayName,
            @Field("profileUrl") String profileUrl,
            @Field("imageUrl") String imageUrl,
            @Field("accessToken") String accessToken,
            @Field("secret") String secret,
            @Field("refreshToken") String refreshToken
    );

}
