package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.conversation.TokenBean;
import com.zack.shop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:05
 * @Package com.zack.shop.mvp.contract
 **/
public interface MainContract  {
    interface View extends IView {

        void connectRongIM(TokenBean data);

        void userInfo(UserBean data);
    }

    interface Model extends IModel {
        Observable<BaseResponse<TokenBean>>  conversationToken();
    }
}
