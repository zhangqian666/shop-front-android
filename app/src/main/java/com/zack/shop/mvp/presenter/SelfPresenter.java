package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.zack.shop.mvp.contract.SelfContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.login.UserBean;
import com.zack.shop.mvp.model.UserModel;
import com.zack.shop.mvp.utils.RxUtils;

import java.io.File;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午4:35
 * @Package com.zack.shop.mvp.presenter
 **/
@FragmentScope
public class SelfPresenter extends BasePresenter<IModel, SelfContract.View> {

    @Inject
    UserModel userModel;

    @Inject
    RxErrorHandler rxErrorHandler;

    @Inject
    public SelfPresenter(SelfContract.View rootView) {
        super(rootView);
    }

    public void getUserInfo() {
        userModel.getUserInfo()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserBean> userBeanBaseResponse) {
                        if (userBeanBaseResponse.isSuccess()) {
                            mRootView.getUserInfo(userBeanBaseResponse.getData());
                        } else {
                            mRootView.showMessage(userBeanBaseResponse.getMsg());
                        }
                    }
                });
    }

    public void updateUserImage(String upload_file) {
        MultipartBody.Part face = MultipartBody.Part.createFormData("upload_file", "header_image.png", RequestBody.create(MediaType.parse("multipart/form-data"), new File(upload_file)));
        userModel.updateUserImage(face)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()){
                            mRootView.updateUserImageSuccess();
                        } else {
                            mRootView.showMessage(stringBaseResponse.getMsg());
                        }
                    }
                });
    }
}
