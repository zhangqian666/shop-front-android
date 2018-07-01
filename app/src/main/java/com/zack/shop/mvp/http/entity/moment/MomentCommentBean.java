package com.zack.shop.mvp.http.entity.moment;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/29 下午5:45
 * @Package com.zack.shop.mvp.http.entity.moment
 **/
@Getter
@Setter
public class MomentCommentBean {
    private Integer id;
    private Integer userId;
    private String username;
    private String userImage;
    private Integer status;
    private Integer followId;
    private Integer replyUserId;
    private String replyUserName;
    private Integer momentsId;
    private String content;
    private String images;
    private int momentPosition;
}
