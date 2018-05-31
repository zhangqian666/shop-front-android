package com.zack.shop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zack.shop.di.module.CategoryModule;
import com.zack.shop.mvp.ui.fragments.CategoryFragment;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/13 下午4:43
 * @Package com.zack.shop.di.component
 **/

@FragmentScope
@Component(modules = CategoryModule.class, dependencies = AppComponent.class)
public interface CategoryComponent {
    void inject(CategoryFragment categoryFragment);
}
