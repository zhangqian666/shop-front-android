package com.zack.shop.mvp.ui.activity.comment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;

public class CommentDetailsActivity extends BaseSupportActivity {


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_comment_details;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
