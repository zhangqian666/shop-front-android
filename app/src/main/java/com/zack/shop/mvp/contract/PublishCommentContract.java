package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午10:35
 * @Package com.zack.shop.mvp.contract
 **/
public interface PublishCommentContract {
    interface Model extends IModel {

    }

    interface View extends IView {

        void getUploadUrls(List<String> data);

        void publishCommentSuccess(String str);
    }
}
