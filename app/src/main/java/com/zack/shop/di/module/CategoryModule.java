package com.zack.shop.di.module;

import android.provider.ContactsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.mvp.contract.CategoryContract;
import com.zack.shop.mvp.http.entity.category.CategoryBean;
import com.zack.shop.mvp.model.CategoryModel;
import com.zack.shop.mvp.ui.adapter.CategoryLeftAdapter;
import com.zack.shop.mvp.ui.adapter.CategoryRightAdapter;

import java.util.ArrayList;
import java.util.List;

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
    public List<CategoryBean> provideCategoryBeans() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    public CategoryRightAdapter provideCategoryRightAdapter(List<CategoryBean> categoryBeans) {
        return new CategoryRightAdapter(categoryBeans);
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
