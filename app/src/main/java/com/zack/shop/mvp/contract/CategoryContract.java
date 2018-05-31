package com.zack.shop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.category.CategoryBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/13 下午4:55
 * @Package com.zack.shop.mvp.contract
 **/
public interface CategoryContract {

    interface Model extends IModel {
        Observable<BaseResponse<List<CategoryBean>>> getCategorys(int parentId);
    }

    interface View extends IView {
        void getCategoryBeanList(List<CategoryBean> data);

        void getItemCategoryBeanList(List<CategoryBean> data);
    }
}
