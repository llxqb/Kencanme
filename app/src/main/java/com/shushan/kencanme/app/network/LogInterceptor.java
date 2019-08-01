package com.shushan.kencanme.app.network;


import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

/**
 * CreateDate   :   16/11/8:下午4:44
 * Author       :   buns
 * PackageName  :   com.freemudpos.data.http
 * Description  :   http log
 */

public class LogInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        assert response.body() != null;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.i("LogInterceptor","\nURL:" + response.request().url() + "（耗时" + (endTime - startTime) + "ms)" + " \nREQEUST BODY:" + bodyToString(request)+
                 "\nResponse BODY:" + content);//+ "\nResponse HEAD:" + response.headers() +
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }

    private static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            assert copy.body() != null;
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
