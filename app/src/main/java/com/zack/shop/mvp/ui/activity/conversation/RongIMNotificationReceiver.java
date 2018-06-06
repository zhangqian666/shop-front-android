package com.zack.shop.mvp.ui.activity.conversation;

import android.content.Context;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午3:25
 * @Package com.zack.shop.mvp.ui.activity.conversation
 **/
public class RongIMNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        return false;
    }
}
