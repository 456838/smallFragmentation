package com.salton123.sf.config;

import com.salton123.sf.util.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/10 15:43
 * Time: 15:43
 * Description:
 */
public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
//        LogUtils.i(String.format("Sending request %s on %s%n%s",
//                request.url(), chain.connection(), request.headers()));
        LogUtils.i(request.url().toString());
        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        LogUtils.i(String.format("Received response %s",
               response.body().string()));
        return response;
    }
}
