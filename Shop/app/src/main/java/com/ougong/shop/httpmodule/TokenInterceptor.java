package com.ougong.shop.httpmodule;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import android.util.Log;
import com.baigui.commonlib.utils.LogUtils;
import com.baigui.commonlib.utils.ToastUtils;
import com.baigui.commonview.App;
import com.ougong.shop.AccountHelper;
import com.ougong.shop.Bean.Netbean;
import com.ougong.shop.Bean.User;
import io.reactivex.functions.Consumer;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import org.json.JSONObject;

/**
 * Created by turbo on 2016/12/20.
 */

class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder requestBuilder = chain.request().newBuilder();
        String token1 = AccountHelper.Companion.getContentToken();

        if (token1 != null && !token1.equals("")) {
            requestBuilder.addHeader("Cookie", "OG-FRONT-TOKEN=" + token1);
        }
        //这是先请求，请求完成，才开始处理
        okhttp3.Response response = chain.proceed(requestBuilder.build());

        List<String> headers = response.headers("Set-Cookie");
        if (headers != null) {
            for (String head : headers) {
                if (head.startsWith("OG-FRONT-TOKEN=")) {
                    String token = head.substring(15).split(";")[0];
                    AccountHelper.Companion.setContentToken(token);
                }

                if (head.startsWith("OG-FRONT-REFRESH-TOKEN=")) {
                    String token = head.substring(23).split(";")[0];
                    AccountHelper.Companion.setRefeshToken(token);
                }
            }

            /**
             * 无论如何，都直接更新sp
             */
            AccountHelper.Companion.updateTokenCache();

        }

        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("UTF8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String bodyString = buffer.clone().readString(charset);

        //response.headers().

        if (isTokenExpired2(bodyString)) {//根据和服务端的约定判断token过期
            //本身的解决办法是如果token过期，同步重新请求token，然后重新组装新的请求（即继续执行原先的请求数据操作）
            if (getNewToken()) {
                //使用新的Token，创建新的请求
                return getResponse2(chain, request, response);
            } else {
                //如果没有获得新的token，就再次请求，也就是把任何每用的东西发给页面，让页面自行判断，
                return getResponse1(chain, request, response);
                //throw new TokenInvalideException();
            }
        }
        return response;
    }

    /**
     * 在请求路径中添加token的方法
     *
     * @param chain
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    private Response getResponse1(Chain chain, Request request, Response response) throws IOException {

        Request newRequest = request.newBuilder()
                .build();
//        response.body().close();
        //重新请求
        return chain.proceed(newRequest);
    }

    /**
     * 在header中添加cookie的方法
     *
     * @param chain
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    private Response getResponse2(Chain chain, Request request, Response response) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String cookie = AccountHelper.Companion.getContentToken();
        builder.addHeader("Cookie", "OG-FRONT-TOKEN=" + cookie);
        response.body().close();
        return chain.proceed(builder.build());
    }

    /**
     * 获取新的token或者cookie
     *
     * @return
     * @throws IOException
     */
    private boolean getNewToken() throws IOException {
        AccountHelper.Companion.setContentToken(null);
        retrofit2.Response<Netbean<String>> a = ServiceFactory.Companion.getInstance().getAccountAPIService().
                refreshToken("OG-FRONT-REFRESH-TOKEN=" + AccountHelper.Companion.getRefeshToken()).execute();

        if (a != null && a.body() != null && a.body().getStatus() == 200) {

            //可以得到新的token，就更新，其他都调出去，然后完全把内容给完全更新了
            for (String head : a.headers().values("Set-Cookie")) {
                if (head.startsWith("OG-FRONT-TOKEN=")) {
                    String token = head.substring(15).split(";")[0];

                    AccountHelper.Companion.setContentToken(token);
                    AccountHelper.Companion.updateTokenCache();

                    return true;
                }

//                if(head.startsWith("OG-FRONT-REFRESH-TOKEN=")){
//                    String token = head.substring(23).split(";")[0];
//                    LogUtils.e(">>>>>>>>>>>>>>>>"+token,this);
//                    AccountHelper.Companion.getUser().setRefeshToken(token);
//                    AccountHelper.Companion.updateUserCache(AccountHelper.Companion.getUser());
//                }

            }

        }
        AccountHelper.Companion.clearUserCache();
//        AccountHelper.Companion.getUser().setContenToken("");
//        AccountHelper.Companion.updateUserCache(AccountHelper.Companion.getUser());
        return false;

    }

    /**
     * 判断coding是否登录 后者cookie过期
     *
     * @param bodyString
     * @return
     */
    private boolean isTokenExpired2(String bodyString) {
        try {
            JSONObject jsonObject = new JSONObject(bodyString);

            /**
             * 过期，或者强制下线，都算cookie过期
             */
            if (jsonObject.getInt("status") == 40003 || jsonObject.getInt("status") == 40006) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}