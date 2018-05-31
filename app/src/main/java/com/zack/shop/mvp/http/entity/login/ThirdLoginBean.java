package com.zack.shop.mvp.http.entity.login;


import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/10 下午2:55
 * @Package com.zack.shop.mvp.http.entity.login
 **/
@Getter
@Setter
public class ThirdLoginBean {
    private String providerId;
    private String providerUserId;
    private String displayName;
    private String profileUrl;
    private String imageUrl;
    private String accessToken;
    private String secret;
    private String refreshToken;
    private Long expireTime;

}
