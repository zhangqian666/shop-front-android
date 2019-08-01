package com.zack.shop.mvp.ui.activity.conversation;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;

public class ConversationActivity extends BaseSupportActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_conversation;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.toolbar_title)).setText(getIntent().getData().getQueryParameter("title"));
        findViewById(R.id.toolbar_back).setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressedSupport() {
        finish();
        super.onBackPressedSupport();
    }
}
