package com.zack.shop.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
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

        setSpanSizeLookup((gridLayoutManager, position) -> {
            if (getData().get(position).getItemType() == CategoryBean.HEADER) {
                return 3;
            } else {
                return 1;
            }
        });
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
