package com.zack.shop.mvp.http.entity.product;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午5:46
 * @Package com.zack.shop.mvp.http.entity.product
 **/
@Getter
@Setter
public class RecommendBean {

    private List<Product> recommendProducts;
    private List<String> recommendImages;
}
