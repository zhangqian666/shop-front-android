package com.zack.shop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.UserDetailsContract;
import com.zack.shop.mvp.model.ProductModel;
import com.zack.shop.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/7/3 下午2:05
 * @Package com.zack.shop.di.module
 **/
@Module
public class UserDetailsModule {
    private UserDetailsContract.View view;

    public UserDetailsModule(UserDetailsContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public UserDetailsContract.View provideBaseView() {
        return view;
    }

    @Provides
    @ActivityScope
    public UserModel provideUserModel(IRepositoryManager iRepositoryManager) {
        return new UserModel(iRepositoryManager);
    }

    @Provides
    @ActivityScope
    public ProductModel provideProductModel(IRepositoryManager iRepositoryManager) {
        return new ProductModel(iRepositoryManager);
    }
}
