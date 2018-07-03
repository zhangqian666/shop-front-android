package com.zack.shop.mvp.http.entity.moment;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/23 下午9:45
 * @Package com.zack.shop.mvp.http.entity
 **/
@Getter
@Setter
public class MomentBean {
    private Integer id;
    private Integer userId;
    private String username;
    private String userImage;
    private Integer category;
    private Integer status;
    private Integer type;
    private String title;
    private String subtitle;
    private String details;
    private String mainImage;
    private String subImages;
    private Long lastSeeTime;
    private Integer star;
    private Integer starEnable;
    private Integer seeTimes;

    private int position;
    private List<MomentCommentBean> momentCommentVoList;
}
