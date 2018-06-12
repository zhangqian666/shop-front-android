package com.zack.shop.mvp.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.moment.MomentBean;
import com.zack.shop.mvp.ui.widget.ImageItemDecoration;

import java.util.Arrays;
import java.util.List;


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

        {
            //点赞处理
            CheckBox star = helper.getView(R.id.tv_bottom_star);
            star.setOnCheckedChangeListener(null);
            star.setEnabled(true);

            if (item.getStarEnable() == 1) {
                star.setChecked(false);
            } else {
                star.setChecked(true);
                star.setEnabled(false);
            }
            star.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    star.setEnabled(false);
                    if (onHeartClickListener != null)
                        onHeartClickListener.onHeartClick(item.getId());
                }
            });

        }

        {
            //图片处理
            if (!TextUtils.isEmpty(item.getSubImages())) {
                String[] split = item.getSubImages().split(",");
                List<String> imageList = Arrays.asList(split);

                RecyclerView imageRecycler = helper.getView(R.id.recycler_image_list);
                //init 图片列表
                if (imageRecycler.getLayoutManager() == null) {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
                    imageRecycler.setLayoutManager(gridLayoutManager);
                }
                if (imageRecycler.getItemDecorationCount() == 0)
                    imageRecycler.addItemDecoration(new ImageItemDecoration(ArmsUtils.dip2px(mContext, 2)));
                imageRecycler.setAdapter(new ImageListAdapter(R.layout.adapter_item_image_list, imageList));
            }
        }


    }

    private OnHeartClickListener onHeartClickListener;

    public void setOnHeartClickListener(OnHeartClickListener onHeartClickListener) {
        this.onHeartClickListener = onHeartClickListener;
    }

    public interface OnHeartClickListener {
        void onHeartClick(int momentId);
    }
}
