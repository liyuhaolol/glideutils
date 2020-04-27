package spa.lyh.cn.lib_image.app;


import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
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
    private static String android = "/Android";


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
    }


    public static void displayImageForNotification(Context context, RemoteViews rv, int resId,
                                                   Notification notification, int NOTIFICATION_ID, String url) {

        if (context != null){
            RoundedCorners corners = new RoundedCorners(dip2px(context,5));
            RequestOptions option = new RequestOptions()
                    .override(300,300)
                    .dontAnimate()
                    .transform(corners);
            Glide.with(context)
                    .asBitmap()
                    .load(syncRes(context,url))
                    .apply(option)
                    .into(new NotificationTarget(context, resId, rv, notification, NOTIFICATION_ID));
        }
    }



    public static void getImageBitmap(Context context, Object res, CustomTarget<Bitmap> target) {
        if (context != null){
            RequestBuilder<Bitmap> builder;
            builder = Glide.with(context)
                    .asBitmap();
            builder.load(syncRes(context,res))
                    .into(target);
        }
    }

    public static void getImageBitmap(Context context, Object res, RequestOptions option, CustomTarget<Bitmap> target) {
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
    }


    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    private static Object syncRes(Context context, Object res){
        if (res instanceof String){
            String path = (String) res;
            if (path.startsWith("/sdcard")){
                String storagePath = Environment.getExternalStorageDirectory().getPath();
                path = storagePath + path.substring(7);
                if (!path.startsWith(storagePath + android)){
                    //外部存储
                    res = IOUtils.getFileUri(context,path);
                }
            }
        }
        return res;
    }
}
