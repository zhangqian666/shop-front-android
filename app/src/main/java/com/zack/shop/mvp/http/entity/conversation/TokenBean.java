package com.zack.shop.mvp.http.entity.conversation;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:01
 * @Package com.zack.shop.mvp.http.entity.conversation
 **/
@Getter
@Setter
public class TokenBean {
    private String token;
    private String userId;
    private Integer code;
    private String msg;
}
