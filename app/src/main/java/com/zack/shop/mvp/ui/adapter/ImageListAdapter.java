package com.zack.shop.mvp.ui.adapter;

import android.content.Intent;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.ui.activity.PhotoViewActivity;
import com.zack.shop.mvp.utils.AppConstant;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/5 下午3:08
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class ImageListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageListAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_item_image_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView itemView = helper.getView(R.id.iv_item);
        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PhotoViewActivity.class);
            intent.putExtra(AppConstant.ActivityIntent.IMAGE_URL, item);
            mContext.startActivity(intent);
        });
        Glide.with(mContext).load(item)
                .into(itemView);

    }

}
