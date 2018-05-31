package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.conversation.TokenBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:00
 * @Package com.zack.shop.mvp.http.api.service
 **/
public interface ConversationService {


    @GET("/rongim/token")
    Observable<BaseResponse<TokenBean>> conversationToken();
}
