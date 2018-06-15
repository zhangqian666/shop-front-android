package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.zack.shop.mvp.contract.AppSetContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.model.UserModel;
import com.zack.shop.mvp.utils.RxUtils;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/14 上午10:43
 * @Package com.zack.shop.mvp.presenter
 **/
@ActivityScope
public class AppSetPresenter extends BasePresenter<IModel, AppSetContract.View> {

    @Inject
    UserModel userModel;

    @Inject
    RxErrorHandler rxErrorHandler;

    @Inject
    public AppSetPresenter(AppSetContract.View rootView) {
        super(rootView);
    }

    public void updateUserPassword(String password){
        userModel.updatePassword(password)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess())mRootView.updatePasswordSuccess();
                    }
                });
    }

    public void updateUserName(String username){
        userModel.updateUserName(username)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess())mRootView.updateUsernameSuccess();
                    }
                });
    }
}
