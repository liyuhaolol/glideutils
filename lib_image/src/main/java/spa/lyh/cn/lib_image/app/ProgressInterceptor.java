package spa.lyh.cn.lib_image.app;

import spa.lyh.cn.lib_image.interceptor.OkInterceptor;
import spa.lyh.cn.lib_image.interceptor.listener.ProgressListener;

public class ProgressInterceptor {

    //入注册下载监听
    public static void addListener(String url, ProgressListener listener) {
        OkInterceptor.addListener(url,listener);
    }

    //取消注册下载监听
    public static void removeListener(String url) {
        OkInterceptor.removeListener(url);
    }
}
