package com.zack.shop.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.moment.ImageChooseBean;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午9:32
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class ImageChooseAdapter extends BaseMultiItemQuickAdapter<ImageChooseBean, BaseViewHolder> {

    public ImageChooseAdapter(List<ImageChooseBean> data) {
        super(data);
        addItemType(ImageChooseBean.URL, R.layout.adapter_item_image_choose);
        addItemType(ImageChooseBean.RES, R.layout.adapter_item_image_choose_res);
    }


    @Override
    protected void convert(BaseViewHolder helper, ImageChooseBean item) {
        switch (item.getItemType()) {
            case ImageChooseBean.URL:
                Glide.with(mContext).load(item.getImageUrl()).into(((ImageView) helper.getView(R.id.iv_item)));
                break;
            case ImageChooseBean.RES:
                break;
        }
    }
}
