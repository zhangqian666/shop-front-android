package com.zack.shop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zack.shop.di.module.OrderManagerListModule;
import com.zack.shop.mvp.ui.activity.order.OrderManageListActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/20 下午2:37
 * @Package com.zack.shop.di.component
 **/
@ActivityScope
@Component(modules = OrderManagerListModule.class, dependencies = AppComponent.class)
public interface OrderManagerListComponent {
    void inject(OrderManageListActivity orderManageListActivity);
}
