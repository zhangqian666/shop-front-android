package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.product.Product;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/21 下午5:22
 * @Package com.zack.shop.mvp.contract
 **/
public interface SearchProductContract {

    interface Model extends IModel {

    }

    interface View extends IView {
        void productListSuccess(List<Product> data);
    }
}
