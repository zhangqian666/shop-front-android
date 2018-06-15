package com.zack.shop.mvp.utils;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/31 上午11:25
 * @Package com.zack.shop.mvp.utils
 **/

public interface AppConstant {


    interface Api {
        String TOKEN = "token";
    }

    interface User {
        String INFO = "userInfo";
    }

    interface ActivityIntent {
        String Bean = "Bean";
        String USER_BEAN = "user_bean";
        String PRODUCT_BEAN = "product_bean";
    }

    interface Image {
        String FILE_PROVIDER = "com.zack.shop.FileProvider";

    }
}
