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
    private RxErrorHandler rxErrorHandler;

    @Inject
    public RecommendPresenter(RecommendContract.Model model, RecommendContract.View rootView, RxErrorHandler rxErrorHandler) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
    }


    public void getRecommend() {
        mModel.getRecommend()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RecommendBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RecommendBean> recommendBeanBaseResponse) {
                        if (recommendBeanBaseResponse.isSuccess()) {
                            mRootView.refreshBannerAndList(recommendBeanBaseResponse.getData());
                        } else {
                            mRootView.showMessage(recommendBeanBaseResponse.getMsg());
                        }
                    }
                });
    }
}
