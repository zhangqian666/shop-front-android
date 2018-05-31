package com.zack.shop.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zack.shop.mvp.http.api.service.UserService;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;


/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:16
 * @Package com.zack.shop.mvp.model
 **/
public class UserModel extends BaseModel {
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<UserBean>> getUserInfo() {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .userDetails();
    }
}
