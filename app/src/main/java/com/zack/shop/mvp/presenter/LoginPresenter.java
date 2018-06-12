package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.zack.shop.mvp.contract.LoginContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.login.JWTBean;
import com.zack.shop.mvp.http.entity.login.UserBean;
import com.zack.shop.mvp.utils.RxUtils;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午5:26
 * @Package com.zack.shop.mvp.presenter
 **/
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    RxErrorHandler rxErrorHandler;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView, RxErrorHandler rxErrorHandler) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
    }

    public void login(String phone, String password) {
        mModel.login(phone, password)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<JWTBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<JWTBean> jwtBeanBaseResponse) {
                        if (jwtBeanBaseResponse.isSuccess()) {
                            mRootView.loginResult(jwtBeanBaseResponse.getData());
                        } else {
                            mRootView.showMessage(jwtBeanBaseResponse.getMsg());
                        }
                    }
                });
    }

    public void sendSms(String phone) {
        mModel.sendSms(phone)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess())
                            mRootView.showMessage(stringBaseResponse.getData());
                        else
                            mRootView.showMessage(stringBaseResponse.getMsg());
                    }
                });
    }

    public void register(String password,
                         String phone,
                         String smsCode) {
        mModel.register(password, phone, smsCode)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserBean> userBeanBaseResponse) {
                        if (userBeanBaseResponse.isSuccess())
                            mRootView.registerSuccess(userBeanBaseResponse.getData());
                        else
                            mRootView.showMessage(userBeanBaseResponse.getMsg());
                    }
                });
    }

}
