package com.zack.shop.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.CategoryContract;
import com.zack.shop.mvp.model.CategoryModel;
import com.zack.shop.mvp.ui.adapter.CategoryLeftAdapter;
import com.zack.shop.mvp.ui.adapter.CategoryRightAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/13 下午4:44
 * @Package com.zack.shop.di.module
 **/
@Module
public class CategoryModule {
    private CategoryContract.View view;

    public CategoryModule(CategoryContract.View view) {
        this.view = view;
    }


    @FragmentScope
    @Provides
    public CategoryLeftAdapter provideCategoryLeftAdapter() {
        return new CategoryLeftAdapter();
    }


    @FragmentScope
    @Provides
    public CategoryRightAdapter provideCategoryRightAdapter() {
        return new CategoryRightAdapter(new ArrayList<>());
    }

    @FragmentScope
    @Provides
    public CategoryContract.Model provideCategoryModel(IRepositoryManager repositoryManager) {
        return new CategoryModel(repositoryManager);
    }

    @FragmentScope
    @Provides
    public CategoryContract.View provideCategoryView() {
        return view;
    }

}
