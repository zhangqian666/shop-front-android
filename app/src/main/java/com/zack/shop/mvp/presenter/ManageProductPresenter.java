package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.zack.shop.mvp.contract.ManageProductContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.product.Product;
import com.zack.shop.mvp.model.ProductModel;
import com.zack.shop.mvp.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/14 下午10:53
 * @Package com.zack.shop.mvp.presenter
 **/
@ActivityScope
public class ManageProductPresenter extends BasePresenter<IModel, ManageProductContract.View> {

    @Inject
    RxErrorHandler rxErrorHandler;
    @Inject
    ProductModel productModel;


    @Inject
    public ManageProductPresenter(ManageProductContract.View rootView) {
        super(rootView);
    }

    public void list() {
        productModel.list(null)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Product>>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Product>> productBaseResponse) {
                        if (productBaseResponse.isSuccess()) {
                            mRootView.productListsuccess(productBaseResponse.getData());
                        } else {
                            mRootView.showMessage(productBaseResponse.getMsg());
                        }
                    }
                });
    }

    public void updateStatus(Integer productId,
                             Integer status) {
        productModel.updateStatus(productId,status)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            mRootView.updateStatusSuccess();
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });
    }
}
