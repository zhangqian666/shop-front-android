package com.zack.shop.di.component;

import android.app.Activity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zack.shop.di.module.RecommendModule;
import com.zack.shop.mvp.ui.fragments.RecommendFragment;
import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午4:50
 * @Package com.zack.shop.di.component
 **/

@FragmentScope
@Component(modules = RecommendModule.class, dependencies = AppComponent.class)
public interface RecommendComponent {
    void inject(RecommendFragment fragment);
}
