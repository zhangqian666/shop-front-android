package com.zack.shop.mvp.ui.adapter;

import android.support.annotation.Nullable;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.category.CategoryBean;

import java.util.List;

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
        helper.setText(R.id.tv_item, item.getName());
        if (helper.getAdapterPosition() == selectedPosition) {
            helper.getView(R.id.ll_item).setBackgroundColor(mContext.getResources().getColor(R.color.white));
            helper.getView(R.id.iv_select).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.ll_item).setBackgroundColor(mContext.getResources().getColor(R.color.text_black_ee));
            helper.getView(R.id.iv_select).setVisibility(View.GONE);
        }
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }
}
