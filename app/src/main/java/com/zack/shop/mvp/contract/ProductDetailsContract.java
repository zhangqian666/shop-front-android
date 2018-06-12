package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.BaseResponse;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/7 下午2:16
 * @Package com.zack.shop.mvp.contract
 **/
public interface ProductDetailsContract {
    interface View extends IView {
    }

    interface Model extends IModel {
        Observable<BaseResponse> addCart(Integer productId, Integer count);
    }
}
