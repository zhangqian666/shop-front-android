package com.zack.shop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zack.shop.di.module.ProductDetailsModule;
import com.zack.shop.mvp.ui.activity.product.ProductDetailsActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/7 下午2:14
 * @Package com.zack.shop.di.component
 **/
@ActivityScope
@Component(modules = ProductDetailsModule.class, dependencies = AppComponent.class)
public interface ProductDetailsComponent {
    void inject(ProductDetailsActivity productDetailsActivity);
}
