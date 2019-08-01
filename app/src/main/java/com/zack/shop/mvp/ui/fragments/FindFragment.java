package com.zack.shop.mvp.ui.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportFragment;
import com.zack.shop.di.component.DaggerFindComponent;
import com.zack.shop.di.module.FindModule;
import com.zack.shop.mvp.contract.FindContract;
import com.zack.shop.mvp.http.entity.moment.CommentBean;
import com.zack.shop.mvp.http.entity.moment.MomentBean;
import com.zack.shop.mvp.presenter.FindPresenter;
import com.zack.shop.mvp.ui.activity.UserDetailsActivity;
import com.zack.shop.mvp.ui.activity.comment.PublishCommentActivity;
import com.zack.shop.mvp.ui.adapter.FindAdapter;
import com.zack.shop.mvp.utils.AppConstant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseSupportFragment<FindPresenter> implements FindContract.View, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recycler_list)
    RecyclerView recyclerFindList;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    FindAdapter findAdapter;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    private LinearLayoutManager linearLayoutManager;
    private int selectCircleItemH;
    private double currentKeyboardH;
    private int screenHeight;
    private CommentBean commentBean;
    private Dialog showInputdialog;
    private int previousKeyboardHeight = -1;

    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .findModule(new FindModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setText("添加");
        toolbarRight.setOnClickListener(v -> {
            startActivity(new Intent(_mActivity, PublishCommentActivity.class));
        });
        toolbarTitle.setText("发现");
        swipeRefreshLayout.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(_mActivity);

        recyclerFindList.setLayoutManager(linearLayoutManager);
        recyclerFindList.setAdapter(findAdapter);
        findAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty, null));
        findAdapter.setOnHeartClickListener(new FindAdapter.OnHeartClickListener() {
            @Override
            public void onHeartClick(int momentId) {
                if (mPresenter != null) {
                    mPresenter.starMoment(momentId);
                }
            }

            @Override
            public void onHeaderClick(Integer userId, String username) {
                Intent intent = new Intent(_mActivity, UserDetailsActivity.class);
                intent.putExtra(AppConstant.User.ID, userId);
                startActivity(intent);
            }

            @Override
            public void onCommentClick(View view, CommentBean item) {
//                new WexinCommentInputView(_mActivity, view, (b, result) -> {
//                    if (mPresenter != null) {
//                        commentBean = item;
//                        mPresenter.publishComment(item.getMomentId(), result, null);
//                    }
//                });
                showInputComment(view, 0, item);
            }

            @Override
            public void onMomentCommentBeanClick(View view, CommentBean item) {
//                new WexinCommentInputView(_mActivity, view, (b, result) -> {
//                    if (mPresenter != null) {
//                        commentBean = item;
//
//                    }
//                });
                showInputComment(view, 0, item);
            }
        });


        _mActivity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            _mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            Integer displayHeight = rect.bottom - rect.top;
            int height = _mActivity.getWindow().getDecorView().getHeight();
            int keyboardHeight = height - displayHeight;
            if (previousKeyboardHeight != keyboardHeight) {
                boolean hide = displayHeight.doubleValue() / height > 0.8;
                if (hide) {
                    if (showInputdialog != null) {
                        showInputdialog.dismiss();
                    }
                }
            }
        });
    }

    private void showInputComment(View commentView, Integer position, CommentBean commentBean) {
        // RV中评论区起始Y的位置
        int rvInputY = getY(commentView);
        int rvInputHeight = commentView.getHeight();

        showInputdialog = new Dialog(_mActivity, android.R.style.Theme_Translucent_NoTitleBar);
        showInputdialog.setContentView(R.layout.view_popup_window);
        showInputdialog.show();

        new Handler().postDelayed(() -> {
            int dialogY = getY(showInputdialog.findViewById(R.id.dialog_layout_comment));
            recyclerFindList.smoothScrollBy(0, rvInputY - (dialogY - rvInputHeight));
        }, 500);

        showInputdialog.findViewById(R.id.tv_commit).setOnClickListener(v -> {
            if (mPresenter != null) {
                String str = ((EditText) showInputdialog.findViewById(R.id.et_comment)).getText().toString().trim();
                mPresenter.publishComment(commentBean.getMomentId(), str, commentBean.getReplyId());
            }
            showInputdialog.dismiss();
        });
    }

    private int getY(View view) {
        int[] rect = new int[2];
        view.getLocationOnScreen(rect);
        return rect[1];

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.getMoments();
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void refreshView(List<MomentBean> data) {
        findAdapter.setNewData(data);

    }

    @Override
    public void starSuccess() {
        if (mPresenter != null) {
            mPresenter.getMoments();
        }
    }

    @Override
    public void commentSuccess() {
        if (mPresenter != null) {
            mPresenter.getMoments();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    /**
     * swipeRefreshLayout 刷新回调
     */
    @Override
    public void onRefresh() {
        if (mPresenter != null) {
            mPresenter.getMoments();
        }

    }


}





