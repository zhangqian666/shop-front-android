package com.zack.shop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zack.shop.di.module.AppSetModule;
import com.zack.shop.mvp.ui.activity.set.AppSetActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/14 上午10:35
 * @Package com.zack.shop.di.component
 **/
@Component(modules = AppSetModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface AppSetComponent {
    void inject(AppSetActivity appSetActivity);
}
