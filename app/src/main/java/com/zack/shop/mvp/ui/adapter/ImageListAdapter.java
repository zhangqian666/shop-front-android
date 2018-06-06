package com.zack.shop.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/5 下午3:08
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class ImageListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(mContext).load(item)
                .into(((ImageView) helper.getView(R.id.iv_item)));
    }

}
