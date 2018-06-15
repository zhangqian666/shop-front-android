package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午4:31
 * @Package com.zack.shop.mvp.contract
 **/
public interface SelfContract {
    interface Model extends IModel {

        Observable<BaseResponse<UserBean>> getUserInfo();

        Observable<BaseResponse<String>> updateUserImage(MultipartBody.Part upload_file);
    }

    interface View extends IView {

        void getUserInfo(UserBean data);

        void updateUserImageSuccess();
    }
}
