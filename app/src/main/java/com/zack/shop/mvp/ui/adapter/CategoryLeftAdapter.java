package com.zack.shop.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.category.CategoryBean;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/13 下午5:11
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class CategoryLeftAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {

    private int selectedPosition = 0;

    public CategoryLeftAdapter() {
        super(R.layout.adapter_item_left_category);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        TextView tvContent = helper.getView(R.id.tv_item);
        tvContent.setText(item.getName());
        helper.setText(R.id.tv_item, item.getName());
        if (helper.getAdapterPosition() == selectedPosition) {
            tvContent.getPaint().setFakeBoldText(true);
            helper.getView(R.id.ll_item).setBackgroundColor(mContext.getResources().getColor(R.color.normal_back_ground));
            helper.getView(R.id.iv_select).setVisibility(View.VISIBLE);
        } else {
            tvContent.getPaint().setFakeBoldText(false);
            helper.getView(R.id.ll_item).setBackgroundColor(mContext.getResources().getColor(R.color.divider_color));
            helper.getView(R.id.iv_select).setVisibility(View.GONE);
        }
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }
}
