package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.cart.CartBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/9 下午1:07
 * @Package com.zack.shop.mvp.contract
 **/
public interface CartContract {
    interface Model extends IModel {

        Observable<BaseResponse<Map<Integer, List<CartBean>>>> list();

        Observable<BaseResponse> addProduct(Integer productId,
                                            Integer count);

        Observable<BaseResponse> deleteProduct(String productIds);

        Observable<BaseResponse<Map<Integer, List<CartBean>>>> selectProduct(
                Integer productId,
                Integer checked);

        Observable<BaseResponse<Map<Integer, List<CartBean>>>> updateProductCount(
                Integer productId,
                Integer count

        );

    }

    interface View extends IView {
        void cartList(List<CartBean> data);

        void deleteProductIdsSuccess();

        void updateProductCount(List<CartBean> baseResponse);
    }
}
