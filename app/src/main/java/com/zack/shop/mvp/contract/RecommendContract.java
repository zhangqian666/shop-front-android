package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.product.RecommendBean;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午5:50
 * @Package com.zack.shop.mvp.contract
 **/


public interface RecommendContract {

    interface Model extends IModel {
        Observable<BaseResponse<RecommendBean>> getRecommend();
    }

    interface View extends IView {
        void refreshBannerAndList(RecommendBean data);
    }
}
