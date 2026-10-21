package spa.lyh.cn.lib_image.app;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.signature.ObjectKey;

import java.io.File;

import spa.lyh.cn.utils_io.IOUtils;

/**
 * Created by liyuhao on 2017/6/7.
 * 图片加载工具
 */

public class ImageLoadUtil {

    public static void displayImage(Context context, Object res, ImageView target) {
        displayImage(context,res,target,"");
    }

    public static void displayImage(Context context, Object res, ImageView target, String signature) {
        displayImage(context,res,target,null,signature);
    }

    public static void displayImage(Context context, Object res, ImageView target, RequestOptions option) {
        displayImage(context,res,target,option,"");
    }

    public static void displayImage(Context context, Object res, ImageView target, RequestOptions option, String signature) {
        try{
            if (context != null){
                RequestBuilder<Drawable> builder;
                builder = Glide.with(context)
                        .asDrawable();
                if (option != null) {
                    builder = builder.apply(option);
                }
                if (!TextUtils.isEmpty(signature)){
                    RequestOptions signatureOption = new RequestOptions().signature(new ObjectKey(signature));
                    builder = builder.apply(signatureOption);
                }
                builder.load(syncRes(context,res))
                        .into(target);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @SuppressLint("MissingPermission")
    public static void displayImageForNotification(Context context, RemoteViews rv, int resId,
                                                   Notification notification, int NOTIFICATION_ID, String url) {
        try{
            if (context != null){
                //通知可能比 Activity 活得久，必须用 ApplicationContext 发起请求
                Context appContext = context.getApplicationContext();
                RoundedCorners corners = new RoundedCorners(dip2px(appContext,5));
                RequestOptions option = new RequestOptions()
                        .override(300,300)
                        .dontAnimate()
                        .transform(corners);
                Glide.with(appContext)
                        .asBitmap()
                        .load(syncRes(appContext,url))
                        .apply(option)
                        .into(new NotificationTarget(appContext, resId, rv, notification, NOTIFICATION_ID));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void getImageBitmap(Context context, Object res, CustomTarget<Bitmap> target) {
        try{
            if (context != null){
                RequestBuilder<Bitmap> builder;
                builder = Glide.with(context)
                        .asBitmap();
                builder.load(syncRes(context,res))
                        .into(target);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getImageBitmap(Context context, Object res, RequestOptions option, CustomTarget<Bitmap> target) {
        try{
            if (context != null){
                RequestBuilder<Bitmap> builder;
                builder = Glide.with(context)
                        .asBitmap();
                if (option != null) {
                    builder = builder.apply(option);
                }
                builder.load(syncRes(context,res))
                        .into(target);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getImageCacheFilepath(Context context, Object res, CustomTarget<File> target) {
        try {
            if (context != null){
                RequestBuilder<File> builder;
                builder = Glide.with(context)
                        .asFile();
                builder.load(syncRes(context,res))
                        .into(target);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getImageCacheFilepath(Context context, Object res, RequestOptions option, CustomTarget<File> target) {
        try{
            if (context != null){
                RequestBuilder<File> builder;
                builder = Glide.with(context)
                        .asFile();
                if (option != null) {
                    builder = builder.apply(option);
                }
                builder.load(syncRes(context,res))
                        .into(target);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    /**
     * 把「文件系统路径字符串」解析成 Glide 可稳定加载的模型。
     *
     * <p>只处理绝对路径（含 {@code /sdcard} 等别名）和 {@code file://}；
     * http(s)、content、android.resource、资源 id、Uri、File 等原样返回。
     * 私有目录 / 共享存储 / 分区存储的判断统一交给 {@link IOUtils#getFileUri}，
     * 避免在这里用字符串前缀猜路径。
     */
    private static Object syncRes(Context context,Object res){
        if (context == null || !(res instanceof String path)){
            return res;
        }
        String fsPath = toFilesystemPath(path);
        if (fsPath == null){
            return res;
        }
        Uri uri = IOUtils.getFileUri(context, fsPath);
        //getFileUri 失败时保留原路径，交给 Glide 走 StringLoader 兜底
        return uri != null ? uri : res;
    }

    /**
     * 仅当入参是本地文件系统路径时返回规范路径，否则返回 null。
     */
    private static String toFilesystemPath(String path){
        if (TextUtils.isEmpty(path)){
            return null;
        }
        //普通绝对路径：/storage/...、/sdcard/...、/data/...
        if (path.startsWith("/")){
            return path;
        }
        //file:// URI，取出 path 后同样走 getFileUri 解析
        if (path.startsWith("file:/")){
            String fsPath = Uri.parse(path).getPath();
            return TextUtils.isEmpty(fsPath) ? null : fsPath;
        }
        return null;
    }
}
