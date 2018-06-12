package com.zack.shop.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.SelfContract;
import com.zack.shop.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午4:32
 * @Package com.zack.shop.di.module
 **/
@Module
public class SelfModule {

    private SelfContract.View view;

    public SelfModule(SelfContract.View view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    public SelfContract.View provideBaseView() {
        return view;
    }

    @Provides
    @FragmentScope
    public UserModel provideBaseModel(IRepositoryManager repositoryManager){
        return new UserModel(repositoryManager);
    }
}
