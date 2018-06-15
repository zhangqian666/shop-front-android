package com.zack.shop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.CreateProductContract;
import com.zack.shop.mvp.model.ProductModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/13 下午1:36
 * @Package com.zack.shop.di.module
 **/

@Module
public class CreateProductModule {

    private CreateProductContract.View view;

    public CreateProductModule(CreateProductContract.View view) {
        this.view = view;
    }


    @Provides
    @ActivityScope
    public ProductModel provideProductModel(IRepositoryManager repositoryManager) {
        return new ProductModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public CreateProductContract.View provideBaseView() {
        return view;
    }
}
