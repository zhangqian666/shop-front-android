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

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/9 下午6:30
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class CartListAdapter extends BaseQuickAdapter<CartBean, BaseViewHolder> {

    public CartListAdapter() {
        super(R.layout.adapter_item_cart);
    }

    private boolean isAllChecked = false;

    @Override
    protected void convert(BaseViewHolder helper, CartBean item) {
        helper.setText(R.id.tv_product_title, item.getProductVo().getName());
        helper.setText(R.id.tv_product_price, String.format("%s", item.getProductVo().getPrice()));
        helper.setText(R.id.tv_producer, String.format("商家：%s", item.getProductVo().getUsername()));
        Glide.with(mContext).load(item.getProductVo().getMainImage()).into(((ImageView) helper.getView(R.id.iv_product)));
        AmountView amountView = helper.getView(R.id.amount_view);
        amountView.setMaxValue(item.getProductVo().getStock());
        amountView.setCurrentAmount(item.getQuantity());
        amountView.setOnAmountChangeListener((view, amount) -> {
            if (onClickCartItemListener != null)
                onClickCartItemListener.onClickAmountCount(item, amount);
        });

        if (helper.getAdapterPosition() != 0 && (getData().get(helper.getAdapterPosition()).getProductVo().getUserId().intValue()
                == getData().get(helper.getAdapterPosition() - 1).getProductVo().getUserId().intValue())) {
            helper.getView(R.id.tv_producer).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_producer).setVisibility(View.VISIBLE);
        }

        {
            CheckBox cb = helper.getView(R.id.btn_checked);
            cb.setOnCheckedChangeListener(null);
            cb.setChecked(isAllChecked);
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (onClickCartItemListener != null) {
                    onClickCartItemListener.onChecked(item, isChecked);
                }
            });
        }

        helper.getView(R.id.iv_product).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProductDetailsActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable(AppConstant.ActivityIntent.Bean,
                    item.getProductVo());
            intent.putExtras(extras);
            mContext.startActivity(intent);
        });


    }

    public void setAllChecked(boolean allChecked) {
        isAllChecked = allChecked;
        notifyDataSetChanged();
    }

    public interface OnClickCartItemListener {
        void onClickAmountCount(CartBean cartBean, int count);

        void onChecked(CartBean cartBean, boolean isChecked);
    }

    private OnClickCartItemListener onClickCartItemListener;

    public void setOnClickCartItemListener(OnClickCartItemListener onClickCartItemListener) {
        this.onClickCartItemListener = onClickCartItemListener;
    }
}
