package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.login.UserBean;
import com.zack.shop.mvp.http.entity.product.Product;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/7/3 下午2:04
 * @Package com.zack.shop.mvp.contract
 **/
public interface UserDetailsContract {
    interface Model extends IModel {
    }

    interface View extends IView {

        void userInfoSuccess(UserBean data);

        void productList(List<Product> data);
    }
}
