package com.zack.shop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zack.shop.di.module.ManageProductModule;
import com.zack.shop.mvp.ui.activity.product.ManageProductActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/14 下午10:52
 * @Package com.zack.shop.di.component
 **/
@ActivityScope
@Component(modules = ManageProductModule.class, dependencies = AppComponent.class)
public interface ManageProductComponent {
    void inject(ManageProductActivity manageProductActivity);
}
