package com.zack.shop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zack.shop.di.module.LoginModule;
import com.zack.shop.mvp.ui.activity.login.LoginActivity;
import com.zack.shop.mvp.ui.activity.login.RegisterActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午5:24
 * @Package com.zack.shop.di.component
 **/
@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);

    void inject(RegisterActivity registerActivity);
}
