package com.zack.shop.mvp.ui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportFragment;
import com.zack.shop.mvp.ui.utils.SpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelfFragment extends BaseSupportFragment {


    public SelfFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_self, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        SpUtils.put(_mActivity, "token", "");
    }

    @Override
    public void setData(@Nullable Object data) {

    }
}
