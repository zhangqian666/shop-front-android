package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.zack.shop.mvp.contract.CartContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.cart.CartBean;
import com.zack.shop.mvp.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        mModel.list().subscribeOn(Schedulers.io())
                .map(mapBaseResponse -> {
                    if (mapBaseResponse.isSuccess()) {
                        mRootView.showMessage(mapBaseResponse.getMsg());
                    }
                    List<CartBean> cartBeans = new ArrayList<>();
                    Map<Integer, List<CartBean>> data = mapBaseResponse.getData();
                    for (Map.Entry<Integer, List<CartBean>> entry : data.entrySet()) {
                        cartBeans.addAll(entry.getValue());
                    }

                    return cartBeans;
                })
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏进度条
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<CartBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(List<CartBean> baseResponse) {
                        mRootView.cartList(baseResponse);

                    }
                });


    }

    public void selectProduct(Integer productId,
                              Integer checked) {
        mModel.selectProduct(productId, checked)
                .map(mapBaseResponse -> {
                    if (mapBaseResponse.isSuccess()) {
                        mRootView.showMessage(mapBaseResponse.getMsg());
                    }
                    List<CartBean> cartBeans = new ArrayList<>();
                    Map<Integer, List<CartBean>> data = mapBaseResponse.getData();
                    for (Map.Entry<Integer, List<CartBean>> entry : data.entrySet()) {
                        cartBeans.addAll(entry.getValue());
                    }

                    return cartBeans;
                })
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<CartBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(List<CartBean> baseResponse) {
                        mRootView.cartList(baseResponse);

                    }
                });
    }

    public void updateProductCount(Integer productId,
                                   Integer count) {
        mModel.updateProductCount(productId, count)
                .map(mapBaseResponse -> {
                    if (mapBaseResponse.isSuccess()) {
                        mRootView.showMessage(mapBaseResponse.getMsg());
                    }
                    List<CartBean> cartBeans = new ArrayList<>();
                    Map<Integer, List<CartBean>> data = mapBaseResponse.getData();
                    for (Map.Entry<Integer, List<CartBean>> entry : data.entrySet()) {
                        cartBeans.addAll(entry.getValue());
                    }

                    return cartBeans;
                })
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<CartBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(List<CartBean> baseResponse) {
                        mRootView.updateProductCount(baseResponse);

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
