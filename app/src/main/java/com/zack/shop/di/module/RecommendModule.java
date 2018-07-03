
package com.zack.shop.di.module;

import android.app.Application;
import android.view.LayoutInflater;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.zack.shop.R;
import com.zack.shop.mvp.contract.RecommendContract;
import com.zack.shop.mvp.http.entity.product.Product;
import com.zack.shop.mvp.model.ProductModel;
import com.zack.shop.mvp.ui.adapter.RecommendQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午4:52
 * @Package com.zack.shop.di.module
 **/

@Module
public class RecommendModule {
    private RecommendContract.View view;

    public RecommendModule(RecommendContract.View view) {
        this.view = view;
    }


    @FragmentScope
    @Provides
    public List<Product> provideRecommendProductsBean() {
        return new ArrayList<>();
    }


    @FragmentScope
    @Provides
    public RecommendQuickAdapter prodvideBaseQuickAdapter(
            List<Product> recommendProductsBeans, Application application) {
        RecommendQuickAdapter recommendQuickAdapter = new RecommendQuickAdapter(recommendProductsBeans);
        recommendQuickAdapter.addHeaderView(LayoutInflater.from(application).inflate(R.layout.layout_recommend_for_you, null));
        return recommendQuickAdapter;

    }

    @FragmentScope
    @Provides
    public RecommendContract.View provideView() {
        return view;
    }

    @FragmentScope
    @Provides
    public RecommendContract.Model provideModel(IRepositoryManager iRepositoryManager) {
        return new ProductModel(iRepositoryManager);
    }

}
