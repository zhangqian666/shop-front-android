package com.zack.shop.mvp.ui.widget;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午7:30
 * @Package com.zack.shop.mvp.ui.widget
 **/


public class ImageItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public ImageItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position % 3 == 1) {
            outRect.top = space;
//            outRect.left = space;
//            outRect.right = space;
        } else if (position % 3 == 2) {
            outRect.top = space;
        } else {
            outRect.top = space;
        }
    }
}
