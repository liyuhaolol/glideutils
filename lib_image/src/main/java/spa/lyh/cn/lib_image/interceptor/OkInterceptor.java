package spa.lyh.cn.lib_image.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import spa.lyh.cn.lib_image.interceptor.listener.ProgressListener;

/**
 * 描述:
 * <p>
 * 拦截器
 * Created by liyuhao on 2018/1/8.
 */

public class OkInterceptor implements Interceptor {

    public static final Map<String, ProgressListener> LISTENER_MAP = new HashMap<>();

    //入注册下载监听
    public static void addListener(String url, ProgressListener listener) {
        LISTENER_MAP.put(url, listener);
    }

    //取消注册下载监听
    public static void removeListener(String url) {
        LISTENER_MAP.remove(url);
    }


    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().toString();
        ResponseBody body = response.body();
        return response.newBuilder().body(new ProgressResponseBody(url, body)).build();
    }
}