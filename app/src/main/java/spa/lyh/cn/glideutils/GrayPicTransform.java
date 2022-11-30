package spa.lyh.cn.glideutils;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.BitmapTransformation;

public class GrayPicTransform extends BitmapTransformation {
    @Override
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return convertGrayImg(toTransform);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }



    /**
     * 彩图转换成灰色图片
     *
     * @param img
     * @return
     */
    public Bitmap convertGrayImg(Bitmap img) {
        int width = img.getWidth();         //获取位图的宽
        int height = img.getHeight();       //获取位图的高

        int[] pixels = new int[width * height]; //通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int gray;
                if (pixels[width * i + j] == 0) {
                    continue;
                } else gray = pixels[width * i + j];

                int red = ((gray & 0x00FF0000) >> 16);
                int green = ((gray & 0x0000FF00) >> 8);
                int blue = (gray & 0x000000FF);

                gray = (int) ((float) red * 0.44 + (float) green * 0.45 + (float) blue * 0.11);
                gray = alpha | (gray << 16) | (gray << 8) | gray;
                pixels[width * i + j] = gray;
            }
        }
        //创建空的bitmap时，格式一定要选择ARGB_4444,或ARGB_8888,代表有Alpha通道，RGB_565格式的不显示灰度
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }
}
