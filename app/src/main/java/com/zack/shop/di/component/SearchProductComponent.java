package com.zack.shop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zack.shop.di.module.SearchProductModule;
import com.zack.shop.mvp.ui.activity.product.SearchProductListActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/21 下午5:21
 * @Package com.zack.shop.di.component
 **/

@ActivityScope
@Component(modules = SearchProductModule.class, dependencies = AppComponent.class)
public interface SearchProductComponent {
    void inject(SearchProductListActivity searchProductListActivity);
}
