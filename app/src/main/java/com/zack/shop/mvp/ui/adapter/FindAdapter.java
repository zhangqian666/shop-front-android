package com.zack.shop.mvp.ui.adapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.moment.CommentBean;
import com.zack.shop.mvp.http.entity.moment.MomentBean;
import com.zack.shop.mvp.http.entity.moment.MomentCommentBean;
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
        helper.setText(R.id.tv_bottom_comment, String.format("%s", item.getMomentCommentVoList() == null ? 0 : item.getMomentCommentVoList().size()));


        Glide.with(mContext)
                .load(item.getUserImage())
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                .into(((ImageView) helper.getView(R.id.iv_user_image)));

        helper.getView(R.id.ll_user).setOnClickListener(v -> {
            if (onHeartClickListener != null)
                onHeartClickListener.onHeaderClick(item.getUserId(), item.getUsername());
        });

        helper.getView(R.id.iv_bottom_comment).setOnClickListener(v -> {
            if (onHeartClickListener != null) {
                CommentBean commentBean = new CommentBean();
                commentBean.setMomentId(item.getId());
                commentBean.setPosition(helper.getAdapterPosition());
                onHeartClickListener.onCommentClick(helper.getView(R.id.iv_bottom_comment), commentBean);
            }
        });


        {
            //点赞处理
            CheckBox star = helper.getView(R.id.cb_bottom_star);
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
                imageRecycler.setAdapter(new ImageListAdapter(imageList));
            }
        }


        initCommentList(helper, item);
    }

    private void initCommentList(BaseViewHolder helper, MomentBean item) {
        RecyclerView commentList = helper.getView(R.id.recycler_comment_list);
        if (commentList.getLayoutManager() == null)
            commentList.setLayoutManager(new LinearLayoutManager(mContext));
        FindCommentAdapter findCommentAdapter = new FindCommentAdapter();
        commentList.setAdapter(findCommentAdapter);
        List<MomentCommentBean> momentCommentVoList = item.getMomentCommentVoList();
        if (momentCommentVoList != null && momentCommentVoList.size() > 0) {
            commentList.setVisibility(View.VISIBLE);
            findCommentAdapter.setNewData(momentCommentVoList);
        } else commentList.setVisibility(View.GONE);

        findCommentAdapter.setOnFindCommentAdapterClickListener((view, commentBean) -> {
            if (onHeartClickListener != null) {
                commentBean.setPosition(helper.getAdapterPosition());
                onHeartClickListener.onMomentCommentBeanClick(view, commentBean);
            }
        });

    }

    private OnHeartClickListener onHeartClickListener;

    public void setOnHeartClickListener(OnHeartClickListener onHeartClickListener) {
        this.onHeartClickListener = onHeartClickListener;
    }

    public interface OnHeartClickListener {
        void onHeartClick(int momentId);

        void onHeaderClick(Integer userId, String username);

        void onCommentClick(View view, CommentBean item);

        void onMomentCommentBeanClick(View view, CommentBean momentCommentBean);

    }
}
