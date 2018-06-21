package com.zack.shop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.OrderManageListContract;
import com.zack.shop.mvp.model.OrderModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/20 下午2:37
 * @Package com.zack.shop.di.module
 **/
@Module
public class OrderManagerListModule {
    private OrderManageListContract.View view;

    public OrderManagerListModule(OrderManageListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public OrderManageListContract.View provideBaseView() {
        return view;
    }

    @ActivityScope
    @Provides
    public OrderModel provideOrderModel(IRepositoryManager iRepositoryManager) {
        return new OrderModel(iRepositoryManager);
    }
}
