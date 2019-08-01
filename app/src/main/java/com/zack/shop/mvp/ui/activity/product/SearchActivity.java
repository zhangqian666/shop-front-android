package com.zack.shop.mvp.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;
import com.zack.shop.mvp.utils.AppConstant;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseSupportActivity {


    @BindView(R.id.et_search)
    EditText etSearch;

    private String currentStr = "";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentStr = s.toString();
            }
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (event.getAction() == KeyEvent.KEYCODE_SEARCH) {

                return true;
            }
            return false;
        });
    }


    @OnClick(R.id.tv_finish)
    public void onViewClicked() {
        if (TextUtils.isEmpty(currentStr)) {
            ArmsUtils.snackbarText("搜索内容不能为空");
            return;
        }
        String replace = currentStr.replace(" ", "");
        replace = replace.trim();
        Intent intent = new Intent(mContext, SearchProductListActivity.class);
        intent.putExtra(AppConstant.ActivityIntent.SEARCH_CONTENT, replace);
        startActivity(intent);
        finish();
    }
}
