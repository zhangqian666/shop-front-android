package com.zack.shop.mvp.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.cart.CartBean;
import com.zack.shop.mvp.ui.activity.product.ProductDetailsActivity;
import com.zack.shop.mvp.ui.widget.AmountView;
import com.zack.shop.mvp.utils.AppConstant;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/12 下午4:56
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class CartInnerListAdapter extends BaseQuickAdapter<CartBean, BaseViewHolder> {
    public CartInnerListAdapter(List<CartBean> cartVos) {
        super(R.layout.adapter_item_cart, cartVos);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartBean item) {
        helper.setText(R.id.tv_product_title, item.getProductVo().getName());
        helper.setText(R.id.tv_product_subtitle, item.getProductVo().getSubtitle());
        helper.setText(R.id.tv_product_price, String.format("%s", item.getProductVo().getPrice()));
        Glide.with(mContext).load(item.getProductVo().getMainImage()).into(((ImageView) helper.getView(R.id.iv_product)));
        {
            AmountView amountView = helper.getView(R.id.amount_view);
            amountView.setMaxValue(item.getProductVo().getStock());
            amountView.setCurrentAmount(item.getQuantity());
            amountView.setOnAmountChangeListener((view, amount) -> {
                if (onClickCartItemListener != null)
                    onClickCartItemListener.onClickAmountCount(view, item, amount);
            });
        }
        {

            CheckBox checkBox = helper.getView(R.id.btn_checked);
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(item.isChecked());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.setChecked(isChecked);
                if (onClickCartItemListener != null)
                    onClickCartItemListener.onAllChecked(isAllChecked());
            });
        }

        helper.getView(R.id.iv_product).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProductDetailsActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable(AppConstant.ActivityIntent.BEAN, item.getProductVo());
            intent.putExtras(extras);
            mContext.startActivity(intent);
        });


    }

    private boolean isAllChecked() {
        boolean isAllChecked = true;
        for (CartBean cb : getData()) {
            if (!cb.isChecked()) {
                isAllChecked = false;
            }
        }
        return isAllChecked;
    }

    public void setAllChecked(boolean isChecked) {
        for (CartBean cb : getData()) cb.setChecked(isChecked);
        notifyDataSetChanged();
    }


    public interface OnClickCartItemListener {
        void onClickAmountCount(View view, CartBean cartBean, int count);

        void onAllChecked(boolean isAllChecked);
    }

    private OnClickCartItemListener onClickCartItemListener;

    public void setOnClickCartItemListener(OnClickCartItemListener onClickCartItemListener) {
        this.onClickCartItemListener = onClickCartItemListener;
    }
}
