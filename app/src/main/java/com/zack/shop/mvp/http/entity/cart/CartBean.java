package com.zack.shop.mvp.http.entity.cart;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zack.shop.mvp.http.entity.product.Product;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/9 下午6:14
 * @Package com.zack.shop.mvp.http.entity.cart
 **/
@Getter
@Setter
public class CartBean implements MultiItemEntity {

    private Integer userId;
    private Integer productId;
    private Integer quantity;
//    private Integer checked;
    private Product productVo;

    private boolean isChecked;

    final public static int ITEM = 1;
    final public static int ITEM_WITH_HEADER = 0;


    private int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }
}
