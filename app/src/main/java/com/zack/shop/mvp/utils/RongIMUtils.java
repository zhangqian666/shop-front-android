package com.zack.shop.mvp.utils;

import android.content.Context;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import timber.log.Timber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 上午11:21
 * @Package com.zack.shop.mvp.utils
 **/
public class RongIMUtils {

    private static boolean isConnected = false;

    public static void connect(Context mContext, String token) {
        Timber.e(mContext.getApplicationInfo().packageName + "====" + Thread.currentThread().getName());
        if (true) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    Timber.e("融云token 错误");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    isConnected = true;
                    Timber.e("连接融云成功"+userid);

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    isConnected = false;
                    Timber.e("连接融云失败");
                }
            });
        }
    }
}
