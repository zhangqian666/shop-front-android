package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/13 下午1:36
 * @Package com.zack.shop.mvp.contract
 **/
public interface CreateProductContract {

    interface View extends IView {

        void getUploadUrls(List<String> data);
    }

    interface Model extends IModel {

    }
}
