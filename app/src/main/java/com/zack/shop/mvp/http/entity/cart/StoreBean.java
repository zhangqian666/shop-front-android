package com.zack.shop.mvp.http.entity.cart;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/12 下午4:28
 * @Package com.zack.shop.mvp.http.entity.cart
 **/
@Getter
@Setter
public class StoreBean {
    private Integer userId;
    private String username;
    private List<CartBean> cartVos;
    private boolean isChecked;
}
