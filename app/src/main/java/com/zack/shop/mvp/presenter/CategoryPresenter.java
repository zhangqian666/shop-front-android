package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.mvp.contract.CategoryContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.category.CategoryBean;
import com.zack.shop.mvp.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/13 下午4:48
 * @Package com.zack.shop.mvp.presenter
 **/
@FragmentScope
public class CategoryPresenter extends BasePresenter<CategoryContract.Model, CategoryContract.View> {
    private RxErrorHandler rxErrorHandler;

    @Inject
    public CategoryPresenter(CategoryContract.Model model, CategoryContract.View rootView, RxErrorHandler rxErrorHandler) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
    }

    public void getCategorys(int parentId) {
        mModel.getCategorys(parentId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<CategoryBean>>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<CategoryBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            if (parentId == 0) {
                                mRootView.getCategoryBeanList(listBaseResponse.getData());
                            } else {
                                List<CategoryBean> newCategoryBeans = new ArrayList<>();
                                for (CategoryBean cb : listBaseResponse.getData()) {
                                    cb.setItemType(CategoryBean.HEADER);
                                    newCategoryBeans.add(cb);
                                    if (!ArmsUtils.isEmpty(cb.getItemList())) {
                                        for (CategoryBean cbitem : cb.getItemList()) {
                                            cbitem.setItemType(CategoryBean.ITEM);
                                            newCategoryBeans.add(cbitem);
                                        }
                                    }
                                }
                                mRootView.getItemCategoryBeanList(newCategoryBeans);
                            }
                        } else {
                            mRootView.showMessage(listBaseResponse.getMsg());
                        }
                    }
                });
    }
}
