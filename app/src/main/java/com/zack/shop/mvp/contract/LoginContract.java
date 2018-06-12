package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.login.JWTBean;
import com.zack.shop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午5:21
 * @Package com.zack.shop.mvp.contract
 **/
public interface LoginContract {

    interface View extends IView {
        void loginResult(JWTBean msg);

        void registerSuccess(UserBean userBeanBaseResponse);
    }

    interface Model extends IModel {
        Observable<BaseResponse<JWTBean>> login(String phone, String password);

        Observable<BaseResponse<String>> sendSms(String phone);

        Observable<BaseResponse<UserBean>> register(String password,
                                                    String phone,
                                                    String smsCode);
    }
}
