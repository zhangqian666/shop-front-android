package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.zack.shop.mvp.contract.FindContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.moment.MomentBean;
import com.zack.shop.mvp.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/23 下午9:39
 * @Package com.zack.shop.mvp.presenter
 **/
@FragmentScope
public class FindPresenter extends BasePresenter<FindContract.Model, FindContract.View> {
    private RxErrorHandler rxErrorHandler;

    @Inject
    public FindPresenter(FindContract.Model model, FindContract.View rootView, RxErrorHandler rxErrorHandler) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
    }

    public void getMoments() {
        mModel.getMoments()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<MomentBean>>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<MomentBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.refreshView(listBaseResponse.getData());
                        } else {
                            mRootView.showMessage(listBaseResponse.getMsg());
                        }
                    }
                });
    }

    public void starMoment(int momentId) {
        mModel.starMoment(momentId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) mRootView.starSuccess();
                        else mRootView.showMessage(baseResponse.getMsg());
                    }
                });
    }

    public void publishComment(Integer momentsId,
                               String content, Integer replyId) {
        mModel.momentsComment(momentsId, content, replyId, null)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) mRootView.commentSuccess();
                        else mRootView.showMessage(baseResponse.getMsg());
                    }
                });

    }


}
