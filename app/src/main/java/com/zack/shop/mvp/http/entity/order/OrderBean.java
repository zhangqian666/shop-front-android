package com.zack.shop.mvp.http.entity.order;

import com.zack.shop.mvp.http.entity.ship.ShippingBean;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/20 上午11:00
 * @Package com.zack.shop.mvp.http.entity.order
 **/
@Setter
@Getter
public class OrderBean {
    private Long orderNo;
    private Integer userId;
    private Integer shippingId;
    private double payment;
    private Integer paymentType;
    private Integer settlementId;
    private Integer postage;
    private Integer status;
    private String paymentTime;
    private String sendTime;
    private String endTime;
    private String closeTime;
    private OrderShopBean orderShopVo;
    private ShippingBean shipping;
}
