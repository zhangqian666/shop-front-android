package com.zack.shop.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zack.shop.mvp.contract.ProductDetailsContract;
import com.zack.shop.mvp.contract.RecommendContract;
import com.zack.shop.mvp.http.api.service.CartService;
import com.zack.shop.mvp.http.api.service.ProductService;
import com.zack.shop.mvp.http.api.service.UploadService;
import com.zack.shop.mvp.http.entity.BaseResponse;
import com.zack.shop.mvp.http.entity.product.Product;
import com.zack.shop.mvp.http.entity.product.RecommendBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午4:53
 * @Package com.zack.shop.mvp.model
 **/


public class ProductModel extends BaseModel implements RecommendContract.Model, ProductDetailsContract.Model {

    public ProductModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<RecommendBean>> getRecommend() {
        return mRepositoryManager
                .obtainRetrofitService(ProductService.class)
                .getRecommendedProducts(0, 0, "");
    }

    public Observable<BaseResponse<List<Product>>> list(Integer userId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .list(userId);
    }


    public Observable<BaseResponse<List<Product>>> searchProductByKeyWordOrCategoryId(String keyword,
                                                                                      Integer categoryId,
                                                                                      int pageNum,
                                                                                      int pageSize,
                                                                                      String orderBy) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .searchProductByKeyWordOrCategoryId(keyword, categoryId, pageNum, pageSize, orderBy);
    }

    public Observable<BaseResponse> updateStatus(Integer productId,
                                                 Integer status) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .updateStatus(productId, status);
    }

    public Observable<BaseResponse> addCart(Integer productId, Integer count) {
        return mRepositoryManager
                .obtainRetrofitService(CartService.class)
                .addProduct(productId, count);
    }

    public Observable<BaseResponse<Product>> createProduct(
            Integer id,
            Integer categoryId,
            String name,
            String subtitle,
            String mainImage,
            String subImage,
            double price,
            Integer stock,
            Integer status) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .createProduct(id, categoryId, name, subtitle, mainImage, subImage, "", price, stock, status);
    }

    public Observable<BaseResponse<String>> upLoadImage(MultipartBody.Part upload_file) {
        return mRepositoryManager.obtainRetrofitService(UploadService.class)
                .uploadImage(upload_file);
    }

    public Observable<BaseResponse<List<String>>> upLoadImages(List<MultipartBody.Part> upload_file) {
        return mRepositoryManager.obtainRetrofitService(UploadService.class)
                .uploadImages(upload_file);
    }


}
