package com.zack.shop.mvp.http.entity.moment;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/29 下午5:46
 * @Package com.zack.shop.mvp.http.entity.moment
 **/
@Setter
@Getter
public class MomentDetails {
    MomentBean momentVo;
    List<MomentCommentBean> momentCommentVos;
}
