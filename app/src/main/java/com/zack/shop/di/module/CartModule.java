package com.zack.shop.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.CartContract;
import com.zack.shop.mvp.model.CartModel;
import com.zack.shop.mvp.ui.adapter.CartListAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/9 下午1:05
 * @Package com.zack.shop.di.module
 **/
@Module
public class CartModule {

    private CartContract.View view;

    public CartModule(CartContract.View view) {
        this.view = view;
    }


    @Provides
    @FragmentScope
    public CartListAdapter provideCartListAdapter() {
        return new CartListAdapter();
    }

    @Provides
    @FragmentScope
    public CartContract.View provideBaseView() {
        return view;
    }

    @Provides
    @FragmentScope
    public CartContract.Model provideBaseModel(IRepositoryManager repositoryManager) {
        return new CartModel(repositoryManager);
    }
}
