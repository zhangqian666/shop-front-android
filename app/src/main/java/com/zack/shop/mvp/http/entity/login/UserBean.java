package com.zack.shop.mvp.http.entity.login;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/10 下午4:42
 * @Package com.zack.shop.mvp.http.entity.login
 **/
@Getter
@Setter
public class UserBean implements Serializable {

    private Integer uid;

    /**
     * Database Column Remarks:
     * 用户名
     */
    private String username;

    private String email;

    private String phone;

    /**
     * Database Column Remarks:
     * 性别
     */
    private Integer sex;

    /**
     * Database Column Remarks:
     * 年龄
     */
    private Integer age;

    /**
     * Database Column Remarks:
     * 头像
     */
    private String image;

    /**
     * Database Column Remarks:
     * 预留
     */
    private Integer type;

    /**
     * Database Column Remarks:
     * 角色0 超级管理员 2时商户 2 普通用户
     */
    private Integer role;

}
