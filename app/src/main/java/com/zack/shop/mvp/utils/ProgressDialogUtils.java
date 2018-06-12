package com.zack.shop.mvp.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/11 下午1:12
 * @Package com.zack.shop.mvp.utils
 **/
public class ProgressDialogUtils {
    private ProgressDialog mProgressDialog;

    public ProgressDialogUtils(Context mContext) {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("加载中");
    }


    public static ProgressDialogUtils getInstance(Context mContext) {
        return new ProgressDialogUtils(mContext);
    }

    public ProgressDialogUtils setMessage(String context) {
        mProgressDialog.setMessage(context);
        return this;
    }


    public ProgressDialogUtils show() {
        mProgressDialog.show();
        return this;
    }

    public ProgressDialogUtils dismiss() {
        mProgressDialog.dismiss();
        return this;
    }
}
