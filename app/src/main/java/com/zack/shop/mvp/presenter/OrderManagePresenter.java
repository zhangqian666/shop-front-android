package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.zack.shop.mvp.contract.OrderManageListContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.order.OrderBean;
import com.zack.shop.mvp.model.OrderModel;
import com.zack.shop.mvp.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/20 下午2:32
 * @Package com.zack.shop.mvp.presenter
 **/
@ActivityScope
public class OrderManagePresenter extends BasePresenter<IModel, OrderManageListContract.View> {
    @Inject
    RxErrorHandler rxErrorHandler;


    @Inject
    OrderModel orderModel;

    @Inject
    public OrderManagePresenter(OrderManageListContract.View rootView) {
        super(rootView);
    }

    public void orderList(Integer status) {
        orderModel.orderList(status)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<OrderBean>>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<OrderBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess())
                            mRootView.orderList(listBaseResponse.getData());
                        else mRootView.showMessage(listBaseResponse.getMsg());
                    }
                });
    }

    public void cancelOrder(Long orderNo) {
        orderModel.orderCancel(orderNo)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess())
                            mRootView.cancelOrderSuccess(baseResponse.getData());
                        else mRootView.showMessage(baseResponse.getMsg());
                    }
                });
    }

}
