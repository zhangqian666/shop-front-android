package com.zack.shop.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zack.shop.mvp.contract.CartContract;
import com.zack.shop.mvp.http.api.service.CartService;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.cart.CartBean;
import com.zack.shop.mvp.http.entity.cart.StoreBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/9 下午1:09
 * @Package com.zack.shop.mvp.model
 **/
public class CartModel extends BaseModel implements CartContract.Model {

    public CartModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<List<StoreBean>>> list() {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .list();
    }

    public Observable<BaseResponse> addProduct(Integer productId,
                                               Integer count) {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .addProduct(productId, count);
    }

    public Observable<BaseResponse> deleteProduct(String productIds) {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .deleteProduct(productIds);
    }

    public Observable<BaseResponse<List<StoreBean>>> selectProduct(
            Integer productId,
            Integer checked) {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .selectProduct(productId, checked);
    }

    public Observable<BaseResponse<CartBean>> updateProductCount(
            Integer productId,
            Integer count

    ) {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .updateProductCount(productId, count);
    }


}
