package com.zack.shop.mvp.http.entity.category;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/13 下午4:57
 * @Package com.zack.shop.mvp.http.entity.category
 **/

@Getter
@Setter
public class CategoryBean implements MultiItemEntity {
    private Integer id;
    private Integer parentId;
    private String name;
    private String image;
    private Boolean status;
    private Integer sortOrder;
    private List<CategoryBean> itemList;

   final public static int ITEM = 1;
   final public static int HEADER = 0;

    private int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }
}
