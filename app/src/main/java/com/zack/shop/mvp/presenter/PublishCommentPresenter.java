package com.zack.shop.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.zack.shop.mvp.contract.PublishCommentContract;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.moment.ImageChooseBean;
import com.zack.shop.mvp.model.MomentModel;
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
import timber.log.Timber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午10:38
 * @Package com.zack.shop.mvp.presenter
 **/
@ActivityScope
public class PublishCommentPresenter extends BasePresenter<IModel, PublishCommentContract.View> {

    @Inject
    RxErrorHandler rxErrorHandler;

    @Inject
    ProductModel productModel;

    @Inject
    MomentModel momentModel;

    @Inject
    public PublishCommentPresenter(PublishCommentContract.View rootView) {
        super(rootView);
    }

    public void uploadImages(List<ImageChooseBean> imageChooseBeans) {
//        Map<String, RequestBody> upload_files = new HashMap<>();
//        MediaType parse = MediaType.parse("multipart/form-data");
//        for (int i = 0; i < imageChooseBeans.size(); i++) {
//            File file = new File(imageChooseBeans.get(i).getImageUrl());
//            RequestBody requestBody = RequestBody.create(parse, file);
//            upload_files.put("file" + i + "\":filename=\"" + file.getName(), requestBody);
//        }


        List<File> files = new ArrayList<>();
        for (ImageChooseBean icb : imageChooseBeans) {
            files.add(new File(icb.getImageUrl()));
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
                            mRootView.getUploadUrls(stringBaseResponse.getData());
                        } else {
                            mRootView.showMessage(stringBaseResponse.getMsg());
                        }
                    }
                });
    }

    public void publishComment(String title,
                               String subTitle,
                               String details,
                               String mainImage,
                               List<String> subImages) {

        StringBuilder subImagesBuilder = new StringBuilder();
        for (String image : subImages) {
            subImagesBuilder.append(image).append(",");
        }
//        subImagesBuilder.deleteCharAt(subImagesBuilder.length() - 1);

        momentModel.createMoments(title, subTitle, details, mainImage, subImagesBuilder.toString())
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.publishCommentSuccess("创建成功");
                            Timber.e(baseResponse.getData().toString());
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });

    }


}
