package com.zack.shop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.CreateOrderContract;
import com.zack.shop.mvp.model.OrderModel;
import com.zack.shop.mvp.model.ShippingAddressModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/19 下午7:14
 * @Package com.zack.shop.di.module
 **/
@Module
public class CreateOrderModule {

    private CreateOrderContract.View view;

    public CreateOrderModule(CreateOrderContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public CreateOrderContract.View provideBaseView() {
        return view;
    }

    @Provides
    @ActivityScope
    public OrderModel provideOrderModel(IRepositoryManager iRepositoryManager) {
        return new OrderModel(iRepositoryManager);
    }

    @Provides
    @ActivityScope
    public ShippingAddressModel provideShippingAddressModel(IRepositoryManager iRepositoryManager) {
        return new ShippingAddressModel(iRepositoryManager);
    }


}
