package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.zack.shop.mvp.contract.CreateOrderContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.order.OrderSettlementsBean;
import com.zack.shop.mvp.model.OrderModel;
import com.zack.shop.mvp.utils.RxUtils;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/19 下午7:27
 * @Package com.zack.shop.mvp.presenter
 **/
@ActivityScope
public class CreateOrderPresenter extends BasePresenter<IModel, CreateOrderContract.View> {

    @Inject
    RxErrorHandler rxErrorHandler;
    @Inject
    OrderModel orderModel;

    @Inject
    public CreateOrderPresenter(CreateOrderContract.View rootView) {
        super(rootView);
    }

    public void createPreOrder(String productIds) {
        orderModel.orderPreCreate(productIds)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<OrderSettlementsBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<OrderSettlementsBean> baseResponse) {
                        if (baseResponse.isSuccess())
                            mRootView.createPreOrderSuccess(baseResponse.getData());
                        else mRootView.showMessage(baseResponse.getMsg());
                    }
                });
    }

    public void createOrder(Integer shippingId, String productIds) {
        orderModel.orderCreate(shippingId, productIds)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<OrderSettlementsBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<OrderSettlementsBean> baseResponse) {
                        if (baseResponse.isSuccess())
                            mRootView.createOrderSuccess(baseResponse.getData());
                        else mRootView.showMessage(baseResponse.getMsg());
                    }
                });
    }
}
