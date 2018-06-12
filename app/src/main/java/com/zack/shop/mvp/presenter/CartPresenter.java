package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.zack.shop.mvp.contract.CartContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.cart.CartBean;
import com.zack.shop.mvp.http.entity.cart.StoreBean;
import com.zack.shop.mvp.ui.widget.AmountView;
import com.zack.shop.mvp.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/9 下午6:22
 * @Package com.zack.shop.mvp.presenter
 **/
@FragmentScope
public class CartPresenter extends BasePresenter<CartContract.Model, CartContract.View> {
    private RxErrorHandler rxErrorHandler;

    @Inject
    public CartPresenter(CartContract.Model model, CartContract.View rootView, RxErrorHandler rxErrorHandler) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
    }


    public void list() {
        mModel.list()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏进度条
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<StoreBean>>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<StoreBean>> baseResponse) {
                        mRootView.cartList(baseResponse.getData());

                    }
                });


    }

    public void selectProduct(Integer productId,
                              Integer checked) {
        mModel.selectProduct(productId, checked)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<StoreBean>>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<StoreBean>> baseResponse) {
                        mRootView.cartList(baseResponse.getData());

                    }
                });
    }

    public void updateProductCount(AmountView view, CartBean cartBean,
                                   Integer count) {
        mModel.updateProductCount(cartBean.getProductId(), count)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<CartBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<CartBean> cartBeanBaseResponse) {
                        if (cartBeanBaseResponse.isSuccess()) {
                            cartBean.setQuantity(cartBeanBaseResponse.getData().getQuantity());
                            mRootView.updateProductCount(cartBeanBaseResponse.getData());
                        } else {
                            view.setCurrentAmount(cartBean.getQuantity());
                            mRootView.showMessage(cartBeanBaseResponse.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        view.setCurrentAmount(cartBean.getQuantity());

                    }
                });
    }

    public void deleteProduct(String productIds) {
        mModel.deleteProduct(productIds)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.deleteProductIdsSuccess();
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });
    }

}
