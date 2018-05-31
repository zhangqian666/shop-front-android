package com.zack.shop.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zack.shop.mvp.contract.RecommendContract;
import com.zack.shop.mvp.http.api.service.ProductService;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.product.RecommendBean;
import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午4:53
 * @Package com.zack.shop.mvp.model
 **/


public class ProductModel extends BaseModel implements RecommendContract.Model {

    public ProductModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<RecommendBean>> getRecommend() {
        return mRepositoryManager
                .obtainRetrofitService(ProductService.class)
                .getRecommendedProducts(0, 0, "");

    }
}
