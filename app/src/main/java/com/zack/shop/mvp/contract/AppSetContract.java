package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/15 下午2:52
 * @Package com.zack.shop.mvp.contract
 **/
public interface AppSetContract {
    interface View extends IView {
        void updatePasswordSuccess();

        void updateUsernameSuccess();
    }

    interface Model extends IModel {
    }
}
