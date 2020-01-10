package spa.lyh.cn.glideutils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import spa.lyh.cn.lib_image.app.ImageLoadUtil;
import spa.lyh.cn.lib_image.app.ProgressInterceptor;
import spa.lyh.cn.lib_image.interceptor.listener.ProgressListener;

public class MainActivity extends AppCompatActivity {
    String url = "http://clubimg.club.vmall.com/data/attachment/forum/201904/16/195645grrrm2slvpnftdrv.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTranslucent();
        //隐藏掉导航栏
        setSystemUiVisibility(getWindow().getDecorView(),View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        Toast.makeText(MainActivity.this,"屏幕宽："+widthPixels+" 高："+heightPixels,Toast.LENGTH_SHORT).show();
        ProgressInterceptor.addListener(url, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log.e("progress",progress+"");
            }
        });
        /*RequestOptions options = new RequestOptions()
                .transform(new RoundedCorners(150));*/
        RequestOptions options = new RequestOptions()
                .transform(new CenterCrop());
        ImageLoadUtil.displayImage(this,R.drawable.news,(ImageView) findViewById(R.id.img),options);
        /*ImageLoadUtil.getImageBitmap(this, url, new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Toast.makeText(MainActivity.this,"图片宽："+resource.getWidth()+" 高："+resource.getHeight(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });*/
    }

    /**
     * 非纯色状态栏，比如用图片进入状态栏位置，使用这个方法。如果纯色状态栏使用这个方法，效果与上面一致，但是不再
     * 兼容换肤框架，状态栏颜色需要手动控制。
     */
    public void setTranslucent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            setSystemUiVisibility(window.getDecorView(),View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            //兼容刘海屏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
            getWindow().setAttributes(lp);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }



    /**
     * 设置显示的样式
     * @param decorView
     * @param visibility
     */
    public void setSystemUiVisibility(View decorView,int visibility){
        setSystemUiVisibility(decorView,visibility,true);
    }

    /**
     * 设置显示的样式
     * @param decorView
     * @param visibility
     */
    public void setSystemUiVisibility(View decorView,int visibility,boolean isAddVisibility){
        int oldVis = decorView.getSystemUiVisibility();
        int newVis = oldVis;
        if (isAddVisibility){
            newVis |= visibility;
        }else {
            newVis &= ~visibility;
        }
        if (newVis != oldVis) {
            decorView.setSystemUiVisibility(newVis);
        }
    }
}
