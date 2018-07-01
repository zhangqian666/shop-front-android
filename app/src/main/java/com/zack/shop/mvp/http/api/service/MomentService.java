package com.zack.shop.mvp.http.api.service;

import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.moment.MomentBean;
import com.zack.shop.mvp.http.entity.moment.MomentDetails;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/23 下午9:45
 * @Package com.zack.shop.mvp.http.api.service
 **/
public interface MomentService {

    /**
     * 文章列表
     *
     * @return
     */
    @POST("/user/moments/list")
    Observable<BaseResponse<List<MomentBean>>> getMoments(
    );

    /**
     * 文章点赞
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/user/moments/star")
    Observable<BaseResponse> starMoments(
            @Field("momentId") Integer momentId
    );

    /**
     * 新建文章
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/user/moments/create")
    Observable<BaseResponse> createMoments(
            @Field("title") String title,
            @Field("subTitle") String subTitle,
            @Field("details") String details,
            @Field("mainImage") String mainImage,
            @Field("subImages") String subImages
    );

    /**
     * 文章详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/user/moments/details")
    Observable<BaseResponse<MomentDetails>> momentsDetails(
            @Field("momentId") Integer momentId
    );

    /**
     * 评论文章
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/user/moments/comment/create")
    Observable<BaseResponse> momentsComment(
            @Field("momentsId") Integer momentsId,
            @Field("content") String content,
                @Field("replyId") Integer replyId,
            @Field("images") String images
    );
}
