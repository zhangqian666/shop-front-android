package com.zack.shop.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.moment.CommentBean;
import com.zack.shop.mvp.http.entity.moment.MomentCommentBean;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/22 上午11:24
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class FindCommentAdapter extends BaseQuickAdapter<MomentCommentBean, BaseViewHolder> {
    public FindCommentAdapter() {
        super(R.layout.adapter_item_find_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, MomentCommentBean item) {
        if (item.getReplyUserId() != null) {
            helper.setText(R.id.tv_comment, String.format("%s 回复 %s : %s", item.getUsername(), item.getReplyUserName(), item.getContent()));
        } else {
            helper.setText(R.id.tv_comment, String.format("%s : %s", item.getUsername(), item.getContent()));
        }
        helper.getView(R.id.tv_comment).setOnClickListener(v -> {
            if (onFindCommentAdapterClickListener != null) {
                CommentBean commentBean = new CommentBean();
                commentBean.setReplyId(item.getUserId());
                commentBean.setMomentId(item.getMomentsId());
                onFindCommentAdapterClickListener.onClick(helper.getView(R.id.tv_comment), commentBean);
            }

        });
    }

    public interface OnFindCommentAdapterClickListener {
        void onClick(View view, CommentBean commentBean);
    }

    private OnFindCommentAdapterClickListener onFindCommentAdapterClickListener;


    public void setOnFindCommentAdapterClickListener(OnFindCommentAdapterClickListener onFindCommentAdapterClickListener) {
        this.onFindCommentAdapterClickListener = onFindCommentAdapterClickListener;
    }
}
