package com.zack.shop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zack.shop.di.module.FindModule;
import com.zack.shop.mvp.ui.fragments.FindFragment;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/23 下午9:43
 * @Package com.zack.shop.di.component
 **/
@FragmentScope
@Component(modules = FindModule.class, dependencies = AppComponent.class)
public interface FindComponent {
    void inject(FindFragment findFragment);
}
