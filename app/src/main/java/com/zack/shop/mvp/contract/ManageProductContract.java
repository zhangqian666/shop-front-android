package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.product.Product;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/14 下午10:49
 * @Package com.zack.shop.mvp.contract
 **/
public interface ManageProductContract {

    interface View extends IView {
        void productListsuccess(List<Product> data);

        void updateStatusSuccess();
    }

    interface Model extends IModel {
    }
}
