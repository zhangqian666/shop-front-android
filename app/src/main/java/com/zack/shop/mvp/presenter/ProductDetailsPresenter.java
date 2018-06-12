package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.zack.shop.mvp.contract.ProductDetailsContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.utils.RxUtils;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/7 下午2:30
 * @Package com.zack.shop.mvp.presenter
 **/
@ActivityScope
public class ProductDetailsPresenter extends BasePresenter<ProductDetailsContract.Model, ProductDetailsContract.View> {
    private RxErrorHandler rxErrorHandler;

    @Inject
    public ProductDetailsPresenter(ProductDetailsContract.Model model, ProductDetailsContract.View rootView, RxErrorHandler rxErrorHandler) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
    }

    public void addProduct(Integer productId, Integer count) {
        mModel.addCart(productId, count)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showMessage("加入购物车成功");
                        } else {
                            mRootView.showMessage("加入购物车失败");
                        }

                    }
                });

    }

}
