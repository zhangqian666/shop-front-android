package com.zack.shop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zack.shop.di.module.CreateOrderModule;
import com.zack.shop.mvp.ui.activity.order.CreateOrderActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/19 下午7:26
 * @Package com.zack.shop.di.component
 **/

@ActivityScope
@Component(modules = CreateOrderModule.class, dependencies = AppComponent.class)
public interface CreateOrderComponent {
    void inject(CreateOrderActivity createOrderActivity);

}
