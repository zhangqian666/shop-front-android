package com.zack.shop.app.config.applyOptions;

import com.jess.arms.http.GlobalHttpHandler;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class MyGlobalHttpHandler implements GlobalHttpHandler {
    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        // 统一处理http响应。eg:状态码不是200时，根据状态码做相应的处理。


        return response;
    }

    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        // 统一处理http请求。eg:给request统一添加token或者header以及参数加密等操作

        return request;
    }
}
