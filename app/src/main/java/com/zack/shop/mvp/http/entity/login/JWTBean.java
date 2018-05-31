package com.zack.shop.mvp.http.entity.login;


import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/10 下午2:31
 * @Package com.zack.shop.mvp.http.entity.login
 **/
@Getter
@Setter
public class JWTBean {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String scope;
    private String jti;
}
