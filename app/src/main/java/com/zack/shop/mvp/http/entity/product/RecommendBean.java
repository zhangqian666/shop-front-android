package com.zack.shop.mvp.http.entity.product;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
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

    private List<RecommendProductsBean> recommendProducts;
    private List<String> recommendImages;

    @Getter
    @Setter
    public class RecommendProductsBean implements Serializable{
        /**
         * id : 10002
         * categoryId : 1011
         * userId : 0
         * name : 春季上衣新款
         * subtitle : 2018最新款式
         * price : 0
         * stock : 1000
         * status : 1
         */

        private int id;
        private int categoryId;
        private int userId;
        private String name;
        private String subtitle;
        private String mainImage;
        private String subImages;
        private double price;
        private int stock;
        private int status;

    }
}
