package spa.lyh.cn.lib_image;


import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import spa.lyh.cn.lib_image.interceptor.OkInterceptor;
import spa.lyh.cn.lib_image.interceptor.OkHttpUrlLoader;

/**
 * Created by liyuhao on 2019/11/21.
 */

@GlideModule
public class CustomCachingGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB

        File pathFile = context.getExternalCacheDir();
        if (pathFile != null){
            String pathInner = pathFile.getAbsolutePath();
            builder.setDiskCache(new DiskLruCacheFactory(pathInner, diskCacheSizeBytes));
        }
        //builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //添加拦截器到Glide
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new OkInterceptor());
        OkHttpClient okHttpClient = builder.build();
        registry.replace(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(okHttpClient));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        //完全禁用清单解析
        return false;
    }
}
