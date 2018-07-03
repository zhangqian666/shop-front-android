package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.zack.shop.mvp.contract.RecommendContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.product.RecommendBean;
import com.zack.shop.mvp.utils.RxUtils;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午4:53
 * @Package com.zack.shop.mvp.presenter
 **/


@FragmentScope
public class RecommendPresenter extends BasePresenter<RecommendContract.Model, RecommendContract.View> {
    @Inject
    RxErrorHandler rxErrorHandler;

    @Inject
    public RecommendPresenter(RecommendContract.Model model, RecommendContract.View rootView) {
        super(model, rootView);
    }


    public void getRecommend() {
        mModel.getRecommend()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RecommendBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RecommendBean> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.refreshBannerAndList(baseResponse.getData());
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });
    }
}
