package com.zack.shop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.MainContract;
import com.zack.shop.mvp.model.ConversationModel;
import com.zack.shop.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:04
 * @Package com.zack.shop.di.module
 **/
@Module
public class MainModule {

    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public MainContract.View provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public MainContract.Model provideModel(IRepositoryManager repositoryManager) {
        return new ConversationModel(repositoryManager);
    }


    @ActivityScope
    @Provides
    public UserModel provideUserModel(IRepositoryManager repositoryManager) {
        return new UserModel(repositoryManager);
    }
}
