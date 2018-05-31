package com.zack.shop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.LoginContract;
import com.zack.shop.mvp.model.LoginModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午5:22
 * @Package com.zack.shop.di.module
 **/

@Module
public class LoginModule {
    LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public LoginContract.Model provideModel(IRepositoryManager repositoryManager) {
        return new LoginModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public LoginContract.View provideView() {
        return view;
    }
}
