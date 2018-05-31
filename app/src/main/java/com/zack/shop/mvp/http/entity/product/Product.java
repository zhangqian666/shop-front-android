package com.zack.shop.mvp.http.entity.product;


import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/10 下午3:05
 * @Package com.zack.shop.mvp.http.entity.product
 **/

@Getter
@Setter
public class Product {
    private Integer id;
    private Integer categoryId;
    private Integer userId;
    private String name;
    private String subtitle;
    private String mainImage;
    private double price;
    private Integer stock;
    private Integer status;
    private String subImages;
    private String detail;
}
