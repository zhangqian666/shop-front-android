package com.zack.shop.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.shop.R;
import com.zack.shop.mvp.http.entity.product.Product;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/14 下午11:06
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class ManageProductAdapter extends BaseQuickAdapter<Product, BaseViewHolder> {

    public ManageProductAdapter() {
        super(R.layout.adapter_item_manage_product);
    }

    @Override
    protected void convert(BaseViewHolder helper, Product item) {
        Glide.with(mContext).load(item.getMainImage()).into(((ImageView) helper.getView(R.id.iv_product)));
        helper.setText(R.id.tv_product_title, item.getName());
        helper.setText(R.id.tv_product_subtitle, item.getSubtitle());
        helper.setText(R.id.tv_product_price, String.format("%s", item.getPrice()));

        helper.setText(R.id.btn_update_status, item.getStatus() == 1 ? "下架" : "发布");
        helper.getView(R.id.btn_edit).setOnClickListener(v -> {

        });
        helper.getView(R.id.btn_update_status).setOnClickListener(v -> {
            onClickBtnListener.onClick(item, item.getStatus() == 1);
        });
        helper.getView(R.id.btn_edit).setOnClickListener(v -> {
            onClickBtnListener.onClickEdit(item);
        });
    }

    public OnClickBtnListener onClickBtnListener;

    public interface OnClickBtnListener {

        void onClick(Product item, boolean onSale);

        void onClickEdit(Product item);
    }

    public void setOnClickBtnListener(OnClickBtnListener onClickBtnListener) {
        this.onClickBtnListener = onClickBtnListener;
    }
}
