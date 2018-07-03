package com.zack.shop.mvp.http.entity.order;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/20 上午10:58
 * @Package com.zack.shop.mvp.http.entity.order
 **/

@Getter
@Setter
public class OrderShopBean {
    private Integer shopId;
    private Integer shopSn;
    private Integer userId;
    private String shopName;
    private String shopImg;
    private String shopTel;
    private List<OrderItemBean> orderItemVos;
}
