package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.login.UserBean;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/15 下午2:52
 * @Package com.zack.shop.mvp.contract
 **/
public interface AppSetContract {
    interface View extends IView {
        void updatePasswordSuccess();

        void updateUsernameSuccess();

        void userInfoSuccess(UserBean data);
    }

    interface Model extends IModel {
    }
}
