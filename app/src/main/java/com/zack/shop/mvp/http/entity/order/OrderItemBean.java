package com.zack.shop.mvp.http.entity.order;

import com.zack.shop.mvp.http.entity.product.Product;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/20 上午10:58
 * @Package com.zack.shop.mvp.http.entity.order
 **/

@Setter
@Getter
public class OrderItemBean {
    private Integer id;
    private Long orderNo;
    private Integer userId;
    private Integer productId;
    private String productName;
    private String productImage;
    private Double currentUnitPrice;
    private Integer quantity;
    private Double totalPrice;
    private Product productVo;

}
