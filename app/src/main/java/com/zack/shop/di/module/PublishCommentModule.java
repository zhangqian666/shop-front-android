package com.zack.shop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.PublishCommentContract;
import com.zack.shop.mvp.model.MomentModel;
import com.zack.shop.mvp.model.ProductModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午10:35
 * @Package com.zack.shop.di.module
 **/
@Module
public class PublishCommentModule {
    private PublishCommentContract.View view;

    public PublishCommentModule(PublishCommentContract.View view) {
        this.view = view;
    }


    @Provides
    @ActivityScope
    public PublishCommentContract.View provideBaseView() {
        return view;
    }

    @Provides
    @ActivityScope
    public ProductModel proProductModel(IRepositoryManager repositoryManager) {
        return new ProductModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public MomentModel provideMomentModel(IRepositoryManager repositoryManager) {
        return new MomentModel(repositoryManager);
    }
}
