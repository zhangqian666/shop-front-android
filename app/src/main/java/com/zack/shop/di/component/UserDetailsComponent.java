package com.zack.shop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zack.shop.di.module.UserDetailsModule;
import com.zack.shop.mvp.ui.activity.UserDetailsActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/7/3 下午2:07
 * @Package com.zack.shop.di.component
 **/


@ActivityScope
@Component(modules = UserDetailsModule.class, dependencies = AppComponent.class)
public interface UserDetailsComponent {
    void inject(UserDetailsActivity userDetailsActivity);
}
