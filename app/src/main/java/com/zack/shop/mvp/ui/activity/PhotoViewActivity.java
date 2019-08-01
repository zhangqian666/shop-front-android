package com.zack.shop.mvp.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.di.component.AppComponent;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;
import com.zack.shop.mvp.utils.AppConstant;

import butterknife.BindView;

public class PhotoViewActivity extends BaseSupportActivity {


    @BindView(R.id.photo_view)
    PhotoView photoView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_photo_view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String url = getIntent().getStringExtra(AppConstant.ActivityIntent.IMAGE_URL);
        Glide.with(mContext).load(url).into(photoView);
    }
}
