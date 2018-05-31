package com.zack.shop.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.FindContract;
import com.zack.shop.mvp.model.MomentModel;
import com.zack.shop.mvp.ui.adapter.FindAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/23 下午9:40
 * @Package com.zack.shop.di.module
 **/
@Module
public class FindModule {

    private FindContract.View view;

    public FindModule(FindContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public FindAdapter provideAdapter() {
        return new FindAdapter();
    }

    @FragmentScope
    @Provides
    public FindContract.Model provideModel(IRepositoryManager repositoryManager) {
        return new MomentModel(repositoryManager);
    }

    @FragmentScope
    @Provides
    public FindContract.View provideView() {
        return view;
    }
}
