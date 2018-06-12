package com.zack.shop.mvp.presenter;

import android.net.Uri;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.zack.shop.mvp.contract.MainContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.conversation.TokenBean;
import com.zack.shop.mvp.http.entity.login.UserBean;
import com.zack.shop.mvp.model.UserModel;
import com.zack.shop.mvp.utils.RxUtils;

import javax.inject.Inject;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:09
 * @Package com.zack.shop.mvp.presenter
 **/
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    RxErrorHandler rxErrorHandler;
    UserModel userModel;

    @Inject
    public MainPresenter(MainContract.Model model,
                         MainContract.View rootView,
                         RxErrorHandler rxErrorHandler,
                         UserModel userModel) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
        this.userModel = userModel;
    }


    public void conversationToken() {
        mModel.conversationToken()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<TokenBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<TokenBean> tokenBeanBaseResponse) {
                        if (tokenBeanBaseResponse.isSuccess()) {
                            mRootView.connectRongIM(tokenBeanBaseResponse.getData());
                        } else {
                            mRootView.showMessage(tokenBeanBaseResponse.getMsg());
                        }
                    }
                });
    }

    public void getUserInfo() {
        userModel.getUserInfo()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserBean> userBeanBaseResponse) {
                        if (userBeanBaseResponse.isSuccess()) {
                            mRootView.userInfo(userBeanBaseResponse.getData());
                        } else {
                            mRootView.showMessage(userBeanBaseResponse.getMsg());
                        }
                    }
                });

        RongIM.setUserInfoProvider(s -> {
            userModel.getUserInfo(Integer.parseInt(s))
                    .compose(RxUtils.applySchedulers(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<UserBean>>(rxErrorHandler) {
                        @Override
                        public void onNext(BaseResponse<UserBean> userBeanBaseResponse) {
                            if (userBeanBaseResponse.isSuccess()) {
                                UserBean data = userBeanBaseResponse.getData();
                                RongIM.getInstance().refreshUserInfoCache(
                                        new UserInfo(
                                                data.getUid().toString(),
                                                data.getUsername(),
                                                Uri.parse(data.getImage())));
                            } else {
                                mRootView.showMessage(userBeanBaseResponse.getMsg());
                            }
                        }
                    });
            return null;
        }, true);
    }



}
