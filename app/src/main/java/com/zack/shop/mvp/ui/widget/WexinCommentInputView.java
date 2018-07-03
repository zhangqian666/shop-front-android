package com.zack.shop.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jess.arms.utils.DeviceUtils;
import com.zack.shop.R;

import java.util.Timer;
import java.util.TimerTask;

import timber.log.Timber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/23 下午12:29
 * @Package com.zack.shop.mvp.ui.widget
 **/
public class WexinCommentInputView extends PopupWindow {
    private LiveCommentSendClick liveCommentResult;
    private Context context;
    private EditText popupCommentEdt;
    private View commentView;
    private View view;
    private EditText popup_live_comment_edit;
    private TextView popup_live_comment_send;
    private PopupWindow commentPopup;

    public WexinCommentInputView(Context context, View view, LiveCommentSendClick liveCommentSendClick) {
        super(context);
        this.context = context;
        this.liveCommentResult = liveCommentSendClick;
        this.view = view;
        setPupupWindow();
    }

    @SuppressLint("WrongConstant")
    private void setPupupWindow() {
        if (commentView == null) {
            commentView = LayoutInflater.from(context).inflate(R.layout.view_popup_window, null);
        }
        if (commentPopup == null) {
            // 创建一个PopuWidow对象
            commentPopup = new PopupWindow(commentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        // 设置动画 commentPopup.setAnimationStyle(R.style.popWindow_animation_in2out);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        commentPopup.setFocusable(true);
        // 设置允许在外点击消失
        commentPopup.setOutsideTouchable(true);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        commentPopup.setBackgroundDrawable(new BitmapDrawable());
        //必须加这两行，不然不会显示在键盘上方
        commentPopup.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        commentPopup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // PopupWindow的显示及位置设置
        commentPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        popup_live_comment_edit = commentView.findViewById(R.id.et_comment);
        popup_live_comment_send = commentView.findViewById(R.id.tv_commit);
        //这是布局中发送按钮的监听
        popup_live_comment_send.setOnClickListener(v -> {
            String result = popup_live_comment_edit.getText().toString().trim();
            if (liveCommentResult != null && result.length() != 0) {
                //把数据传出去
                liveCommentResult.onResult(true, result);
                DeviceUtils.hideSoftKeyboard(context, popup_live_comment_edit);
                //关闭popup
                commentPopup.dismiss();
            }
        });
        //设置popup关闭时要做的操作
        commentPopup.setOnDismissListener(() -> {
            Timber.e("setOnDismissListener");
            DeviceUtils.hideSoftKeyboard(context, popup_live_comment_edit);
            popup_live_comment_edit.setText("");
        });
        //显示软键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //此方法就不提供了，网上一大推
                DeviceUtils.showSoftKeyboard(context, popup_live_comment_edit);
            }
        }, 200);

    }

    public interface LiveCommentSendClick {
        void onResult(boolean b, String result);
    }
}
