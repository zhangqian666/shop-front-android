package com.zack.shop.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.CheckedTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.moment.MomentBean;


/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/23 下午10:04
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class FindAdapter extends BaseQuickAdapter<MomentBean, BaseViewHolder> {

    public FindAdapter() {
        super(R.layout.adapter_item_find);
    }

    @Override
    protected void convert(BaseViewHolder helper, MomentBean item) {
        helper.setText(R.id.tv_user_name, item.getUsername());
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_bottom_see, String.format("%s人浏览", item.getSeeTimes()));
        helper.setText(R.id.tv_bottom_star, String.format("%s", item.getStar()));
        helper.setText(R.id.tv_bottom_comment, String.format("%s", item.getMomentCommentTimes()));

        helper.getView(R.id.tv_bottom_star).setOnClickListener(v -> {
            if (onHeartClickListener != null) onHeartClickListener.onHeartClick(item.getId());
        });
    }

    private OnHeartClickListener onHeartClickListener;

    public void setOnHeartClickListener(OnHeartClickListener onHeartClickListener) {
        this.onHeartClickListener = onHeartClickListener;
    }

    public interface OnHeartClickListener {
        void onHeartClick(int momentId);
    }
}
