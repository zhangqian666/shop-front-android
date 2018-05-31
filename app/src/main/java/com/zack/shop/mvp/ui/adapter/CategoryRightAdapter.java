package com.zack.shop.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.category.CategoryBean;

import java.util.List;


/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/13 下午5:54
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class CategoryRightAdapter extends BaseMultiItemQuickAdapter<CategoryBean, BaseViewHolder> {

    public CategoryRightAdapter(List<CategoryBean> data) {
        super(data);
        addItemType(CategoryBean.ITEM, R.layout.adapter_item_right_category);
        addItemType(CategoryBean.HEADER, R.layout.adapter_header_right_category);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        switch (item.getItemType()) {
            case CategoryBean.HEADER:
                helper.setText(R.id.tv_header, item.getName());
                break;
            case CategoryBean.ITEM:
                helper.setText(R.id.tv_item, item.getName());
                if (!ArmsUtils.isEmpty(item.getImage())) {
                    Glide.with(mContext).load(item.getImage()).into(((ImageView) helper.getView(R.id.iv_item)));
                }
                break;

        }
    }
}
