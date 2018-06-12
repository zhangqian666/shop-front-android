package com.zack.shop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.ProductDetailsContract;
import com.zack.shop.mvp.model.ProductModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/7 下午2:14
 * @Package com.zack.shop.di.module
 **/
@Module
public class ProductDetailsModule {
    private ProductDetailsContract.View view;

    public ProductDetailsModule(ProductDetailsContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public ProductDetailsContract.Model provideBaseModel(IRepositoryManager repositoryManager) {
        return new ProductModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public ProductDetailsContract.View provideBaseView() {
        return view;
    }
}
