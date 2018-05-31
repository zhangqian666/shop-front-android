package com.zack.shop.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zack.shop.mvp.contract.MainContract;
import com.zack.shop.mvp.http.api.service.ConversationService;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.conversation.TokenBean;

import io.reactivex.Observable;


/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:03
 * @Package com.zack.shop.mvp.model
 **/
public class ConversationModel extends BaseModel implements MainContract.Model{

    public ConversationModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<TokenBean>> conversationToken() {
        return mRepositoryManager.obtainRetrofitService(ConversationService.class)
                .conversationToken();
    }
}
