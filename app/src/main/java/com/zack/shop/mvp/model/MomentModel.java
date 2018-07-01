package com.zack.shop.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zack.shop.mvp.contract.FindContract;
import com.zack.shop.mvp.http.api.service.MomentService;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.moment.MomentBean;
import com.zack.shop.mvp.http.entity.moment.MomentDetails;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/23 下午9:50
 * @Package com.zack.shop.mvp.model
 **/
public class MomentModel extends BaseModel implements FindContract.Model {

    public MomentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<List<MomentBean>>> getMoments() {
        return mRepositoryManager.obtainRetrofitService(MomentService.class)
                .getMoments();
    }

    public Observable<BaseResponse<MomentDetails>> momentsDetails(int momentId) {
        return mRepositoryManager.obtainRetrofitService(MomentService.class)
                .momentsDetails(momentId);
    }

    public Observable<BaseResponse> starMoment(int momentId) {
        return mRepositoryManager.obtainRetrofitService(MomentService.class)
                .starMoments(momentId);
    }

    public Observable<BaseResponse> createMoments(
            String title,
            String subTitle,
            String details,
            String mainImage,
            String subImages) {
        return mRepositoryManager.obtainRetrofitService(MomentService.class)
                .createMoments(title, subTitle, details, mainImage, subImages);
    }

    public Observable<BaseResponse> momentsComment(
            Integer momentsId,
            String content,
            Integer replyId,
            String images
    ) {
        return mRepositoryManager.obtainRetrofitService(MomentService.class)
                .momentsComment(momentsId, content, replyId, images);
    }

}
