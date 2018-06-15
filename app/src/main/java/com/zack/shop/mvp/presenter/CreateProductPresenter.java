package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.zack.shop.mvp.contract.CreateProductContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.moment.ImageChooseBean;
import com.zack.shop.mvp.http.entity.product.Product;
import com.zack.shop.mvp.model.ProductModel;
import com.zack.shop.mvp.utils.RxUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/13 下午1:48
 * @Package com.zack.shop.mvp.presenter
 **/

@ActivityScope
public class CreateProductPresenter extends BasePresenter<IModel, CreateProductContract.View> {

    @Inject
    ProductModel productModel;
    @Inject
    RxErrorHandler rxErrorHandler;

    @Inject
    public CreateProductPresenter(CreateProductContract.View rootView) {
        super(rootView);
    }


    public void createProduct(Integer id,
                              Integer categoryId,
                              String name,
                              String subtitle,
                              String mainImage,
                              List<String> subImage,
                              double price,
                              Integer stock,
                              Integer status) {
        StringBuilder subImagesBuilder = new StringBuilder();
        for (String image : subImage) {
            subImagesBuilder.append(image).append(",");
        }
        subImagesBuilder.deleteCharAt(subImagesBuilder.length() - 1);

        productModel.createProduct(id, categoryId, name, subtitle, mainImage, subImagesBuilder.toString(), price, stock, status)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Product>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Product> productBaseResponse) {
                        if (productBaseResponse.isSuccess()) {
                            mRootView.showMessage("发布成功");
                            mRootView.killMyself();
                        } else mRootView.showMessage(productBaseResponse.getMsg());
                    }
                });

    }

    private List<String> imageList = new ArrayList<>();

    public void uploadImages(List<ImageChooseBean> imageChooseBeans) {
        List<File> files = new ArrayList<>();
        for (ImageChooseBean icb : imageChooseBeans) {
            if (icb.getImageUrl().contains("http")) {
                imageList.add(icb.getImageUrl());
            } else {
                files.add(new File(icb.getImageUrl()));
            }
        }
        if (files.isEmpty()){
            mRootView.getUploadUrls(imageList);
            return;
        }

        List<MultipartBody.Part> parts = new ArrayList<>();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("upload_file", file.getName(), requestBody);
            parts.add(part);
        }


        productModel.upLoadImages(parts)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<String>>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<String>> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()) {
                            List<String> stringList = stringBaseResponse.getData();
                            stringList.addAll(imageList);
                            imageList.clear();
                            mRootView.getUploadUrls(stringList);
                        } else {
                            mRootView.showMessage(stringBaseResponse.getMsg());
                        }
                    }
                });
    }


}
