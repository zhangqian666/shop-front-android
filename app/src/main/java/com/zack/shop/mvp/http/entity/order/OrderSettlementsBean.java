package com.zack.shop.mvp.http.entity.order;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/21 下午3:12
 * @Package com.zack.shop.mvp.http.entity.order
 **/
@Setter
@Getter
public class OrderSettlementsBean {
    private Integer id;
    private Integer settlementno;
    private Integer settlementtype;
    private Integer shopid;
    private Long settlementmoney;
    private Long ordermoney;
    private Integer isfinish;
    private Long totalcommission;
    private List<OrderBean> orderVos;

}
