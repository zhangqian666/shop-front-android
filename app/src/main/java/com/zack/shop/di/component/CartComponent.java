package com.zack.shop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zack.shop.di.module.CartModule;
import com.zack.shop.mvp.ui.fragments.CartFragment;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/9 下午1:05
 * @Package com.zack.shop.di.component
 **/
@FragmentScope
@Component(modules = CartModule.class, dependencies = AppComponent.class)
public interface CartComponent {
    void inject(CartFragment cartFragment);
}
