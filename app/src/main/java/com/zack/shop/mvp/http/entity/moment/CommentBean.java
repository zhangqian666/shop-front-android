package com.zack.shop.mvp.http.entity.moment;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/7/1 下午10:04
 * @Package com.zack.shop.mvp.http.entity.moment
 **/
@Getter
@Setter
public class CommentBean {
    private Integer momentId;
    private Integer replyId;
    private String content;
    private Integer position;
}
