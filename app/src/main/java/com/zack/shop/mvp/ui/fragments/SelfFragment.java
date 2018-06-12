package com.zack.shop.mvp.ui.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportFragment;
import com.zack.shop.di.component.DaggerSelfComponent;
import com.zack.shop.di.module.SelfModule;
import com.zack.shop.mvp.contract.SelfContract;
import com.zack.shop.mvp.http.entity.login.UserBean;
import com.zack.shop.mvp.presenter.SelfPresenter;
import com.zack.shop.mvp.ui.activity.set.AppSetActivity;
import com.zack.shop.mvp.utils.AppConstant;
import com.zack.shop.mvp.utils.PicChooserHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

import static com.zack.shop.mvp.utils.PicChooserHelper.PicType.Avatar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelfFragment extends BaseSupportFragment<SelfPresenter> implements SelfContract.View {


    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    private PicChooserHelper picChooserHelper;

    public SelfFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSelfComponent.builder()
                .appComponent(appComponent)
                .selfModule(new SelfModule(this))
                .build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_self, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.getUserInfo();
        }

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick({R.id.iv_header, R.id.iv_set, R.id.iv_message, R.id.ll_header, R.id.ll_wait_send, R.id.ll_wait_receive, R.id.ll_pay_after})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                uploadImage();
                break;
            case R.id.iv_set:
                if (userBean != null) {
                    Intent intent = new Intent(_mActivity, AppSetActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable(AppConstant.ActivityIntent.USER_BEAN,
                            userBean);
                    intent.putExtras(extras);
                    _mActivity.startActivity(intent);
                }
                break;
            case R.id.iv_message:
                //启动聊天列表界面
                Map<String, Boolean> supportedConversation = new HashMap<>();
                supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false);
                RongIM.getInstance().startConversationList(_mActivity, supportedConversation);
                break;
            case R.id.ll_header:
                break;
            case R.id.ll_wait_send:
                break;
            case R.id.ll_wait_receive:
                break;
            case R.id.ll_pay_after:
                break;
        }
    }

    public void uploadImage() {
        picChooserHelper = new PicChooserHelper(this, Avatar);
        picChooserHelper.setOnChooseResultListener(url -> {
            if (mPresenter != null) {
                mPresenter.updateUserImage(url);
            }
        });

        final String items[] = {"相机", "图库"};
        AlertDialog.Builder builder = new AlertDialog.Builder(_mActivity, 3);
        builder.setTitle("头像上传");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    picChooserHelper.takePicFromCamera();
                    break;
                case 1:
                    picChooserHelper.takePicFromAlbum();
                    break;
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (picChooserHelper != null)
            picChooserHelper.onActivityResult(requestCode, resultCode, data);
    }

    private UserBean userBean;

    @Override
    public void getUserInfo(UserBean data) {
        Glide.with(_mActivity).load(data.getImage()).into(ivHeader);
        tvName.setText(data.getUsername());
        this.userBean = data;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
}
