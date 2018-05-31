package com.zack.shop.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zack.shop.mvp.contract.CategoryContract;
import com.zack.shop.mvp.http.api.service.CategoryService;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.category.CategoryBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/13 下午4:56
 * @Package com.zack.shop.mvp.model
 **/
public class CategoryModel extends BaseModel implements CategoryContract.Model {

    public CategoryModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<List<CategoryBean>>> getCategorys(int parentId) {
        return mRepositoryManager
                .obtainRetrofitService(CategoryService.class)
                .getCategorys(parentId);
    }

}
