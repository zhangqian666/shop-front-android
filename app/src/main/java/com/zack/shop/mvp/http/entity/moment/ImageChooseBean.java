package com.zack.shop.mvp.http.entity.moment;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午9:54
 * @Package com.zack.shop.mvp.http.entity.moment
 **/
@Getter
@Setter
public class ImageChooseBean implements MultiItemEntity {

    private String imageUrl;
    private int imageRes;

    private int itemType;


    final public static int URL = 0;
    final public static int RES = 1;

    @Override
    public int getItemType() {
        return itemType;
    }
}
