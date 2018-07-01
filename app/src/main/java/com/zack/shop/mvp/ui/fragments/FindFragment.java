package com.zack.shop.mvp.ui.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.zack.shop.mvp.ui.activity.comment.PublishCommentActivity;
import com.zack.shop.mvp.ui.adapter.FindAdapter;
import com.zack.shop.mvp.ui.widget.WexinCommentInputView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.rong.imkit.RongIM;

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
                RongIM.getInstance().startPrivateChat(_mActivity, userId.toString(), username);
            }

            @Override
            public void onCommentClick(View view, CommentBean item) {
                new WexinCommentInputView(_mActivity, view, (b, result) -> {
                    if (mPresenter != null) {
                        commentBean = item;
                        mPresenter.publishComment(item.getMomentId(), result, null);
                    }
                });
            }

            @Override
            public void onMomentCommentBeanClick(View view, CommentBean item) {
                new WexinCommentInputView(_mActivity, view, (b, result) -> {
                    if (mPresenter != null) {
                        commentBean = item;
                        mPresenter.publishComment(item.getMomentId(), result, item.getReplyId());
                    }
                });
            }
        });
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





