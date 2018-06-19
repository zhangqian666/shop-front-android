package com.zack.shop.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zack.shop.mvp.contract.SelfContract;
import com.zack.shop.mvp.http.api.service.UserService;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:16
 * @Package com.zack.shop.mvp.model
 **/
public class UserModel extends BaseModel implements SelfContract.Model {
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    public Observable<BaseResponse<UserBean>> getUserInfo() {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .userDetails(null);
    }

    public Observable<BaseResponse<UserBean>> getUserInfo(int userId) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .userDetails(userId);
    }

    public Observable<BaseResponse> updateInfo(String username, Integer sex) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .updateInfo(username,sex);
    }

    public Observable<BaseResponse> updatePassword(String password) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .updatePassword(password);
    }

    public Observable<BaseResponse<String>> updateUserImage(MultipartBody.Part upload_file) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .updateUserImage(upload_file);
    }
}
