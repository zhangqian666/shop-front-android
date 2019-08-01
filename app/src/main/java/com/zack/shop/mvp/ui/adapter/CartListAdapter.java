package com.zack.shop.mvp.ui.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.cart.CartBean;
import com.zack.shop.mvp.http.entity.cart.StoreBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/9 下午6:30
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class CartListAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {

    public CartListAdapter() {
        super(R.layout.adapter_item_cart_store);
    }


    @Override
    protected void convert(BaseViewHolder helper, StoreBean item) {
        helper.setText(R.id.tv_store_name, item.getUsername());
        initInnerAdapter(helper, item);
    }

    private void initInnerAdapter(BaseViewHolder helper, StoreBean item) {
        RecyclerView innerRecyclerView = helper.getView(R.id.recycler_list);
        innerRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        CartInnerListAdapter cartInnerListAdapter = new CartInnerListAdapter(item.getCartVos());
        innerRecyclerView.setAdapter(cartInnerListAdapter);
        cartInnerListAdapter.setOnClickCartItemListener(new CartInnerListAdapter.OnClickCartItemListener() {
            @Override
            public void onClickAmountCount(View view, CartBean cartBean, int count) {
                if (onClickStoreItemListener != null)
                    onClickStoreItemListener.onClickAmountCount(view, cartBean, count);
            }

            @Override
            public void onAllChecked(boolean isAllChecked) {
                item.setChecked(isAllChecked);
                notifyDataSetChanged();
                if (onClickStoreItemListener != null) {
                    onClickStoreItemListener.onChecked(isAllChecked());
                }
            }
        });

        CheckBox checkBox = helper.getView(R.id.btn_checked);
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(item.isChecked());
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setStoreAllChecked(helper.getAdapterPosition(), isChecked);
            cartInnerListAdapter.setAllChecked(isChecked);
            if (onClickStoreItemListener != null) {
                onClickStoreItemListener.onChecked(isAllChecked());
            }
        });
    }

    private void setStoreAllChecked(int position, boolean isChecked) {
        getData().get(position).setChecked(isChecked);
        for (CartBean cb : getData().get(position).getCartVos())
            cb.setChecked(isChecked);
    }

    public List<CartBean> getAllCheckedCartBean() {
        List<CartBean> cartBeans = new ArrayList<>();
        for (StoreBean sb : getData()) {
            for (CartBean cb : sb.getCartVos()) {
                if (cb.isChecked()) cartBeans.add(cb);
            }
        }
        return cartBeans;
    }


    private boolean isAllChecked() {
        boolean isAllchecked = true;
        for (StoreBean sb : getData()) {
            if (!sb.isChecked()) {
                isAllchecked = false;
            }
        }
        return isAllchecked;
    }

    public void setAllChecked(boolean allChecked) {
        for (StoreBean sb : getData()) {
            sb.setChecked(allChecked);
            for (CartBean cb : sb.getCartVos()) {
                cb.setChecked(allChecked);
            }
        }
        notifyDataSetChanged();
    }

    public interface OnClickStoreItemListener {
        void onChecked(boolean isAllChecked);

        void onClickAmountCount(View view, CartBean cartBean, int count);


    }

    private OnClickStoreItemListener onClickStoreItemListener;

    public void setOnClickStoreItemListener(OnClickStoreItemListener onClickStoreItemListener) {
        this.onClickStoreItemListener =
                onClickStoreItemListener;
    }

}
