package com.zack.shop.mvp.ui.widget;

import android.content.Context;

import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zack.shop.R;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/10 下午6:21
 * @Package com.zack.shop.mvp.ui.widget
 **/
public class SimpleToolbar extends Toolbar {

    private TextView mLeftTextView;
    private TextView mRightTextView;
    private TextView mMainTextView;

    public SimpleToolbar(Context context) {
        super(context);
        initData(context);
    }

    public SimpleToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public SimpleToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        LayoutInflater.from(context).inflate(R.layout.simple_tool_bar, this, true);
        mLeftTextView = findViewById(R.id.txt_left_title);
        mMainTextView = findViewById(R.id.txt_main_title);
        mRightTextView = findViewById(R.id.txt_right_title);
        mLeftTextView.setOnClickListener(v -> {
            if (leftOnclickListener != null) {
                leftOnclickListener.onClick();
            }
        });
        mRightTextView.setOnClickListener(v -> {
            if (rightOnclickListener != null) {
                rightOnclickListener.onClick();
            }
        });
    }

    private SimpleToolbar setLeftText(String leftText) {
        mLeftTextView.setText(leftText);
        return this;
    }

    private SimpleToolbar setDrawableLeft(int leftDrawable) {
        mLeftTextView.setCompoundDrawables(getResources().getDrawable(leftDrawable), null, null, null);
        return this;
    }

    private SimpleToolbar setRightText(String rightText) {
        mRightTextView.setText(rightText);
        return this;
    }

    private SimpleToolbar setDrawableRight(int rightDrawable) {
        mRightTextView.setCompoundDrawables(null, null, getResources().getDrawable(rightDrawable), null);
        return this;
    }

    private SimpleToolbar setTitle(String title) {
        mMainTextView.setText(title);
        return this;
    }

    private OnClickButtonListener leftOnclickListener;

    private OnClickButtonListener rightOnclickListener;

    public void setLeftOnclickListener(OnClickButtonListener leftOnclickListener) {
        this.leftOnclickListener = leftOnclickListener;
    }

    public void setRightOnclickListener(OnClickButtonListener rightOnclickListener) {
        this.rightOnclickListener = rightOnclickListener;
    }

    public interface OnClickButtonListener {
        void onClick();
    }
}
