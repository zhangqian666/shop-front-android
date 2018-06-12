package com.zack.shop.mvp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.engine.ImageEngine;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午9:42
 * @Package com.zack.shop.mvp.ui.activity.find
 **/
public class GlideEngine implements ImageEngine {
    public static ImageEngine getInstance() {
        return new GlideEngine();
    }

    @Override
    public void loadPhoto(Context context, String photoPath, ImageView imageView) {
        Glide.with(context).load(photoPath).into(imageView);
    }

    @Override
    public void loadGifAsBitmap(Context context, String gifPath, ImageView imageView) {

    }

    @Override
    public void loadGif(Context context, String gifPath, ImageView imageView) {

    }

    @Override
    public Bitmap getCacheBitmap(Context context, String path, int width, int height) throws Exception {
        return null;
    }
}
