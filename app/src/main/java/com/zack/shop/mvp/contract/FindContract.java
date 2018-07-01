package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.moment.MomentBean;
import com.zack.shop.mvp.http.entity.moment.MomentDetails;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/23 下午9:40
 * @Package com.zack.shop.mvp.contract
 **/
public interface FindContract {

    interface View extends IView {

        void refreshView(List<MomentBean> data);

        void starSuccess();

        void commentSuccess();
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<MomentBean>>> getMoments();

        Observable<BaseResponse<MomentDetails>> momentsDetails(int momentId);

        Observable<BaseResponse> starMoment(int momentId);

        Observable<BaseResponse> momentsComment(
                Integer momentsId,
                String content,
                Integer replyId,
                String images
        );

    }
}
