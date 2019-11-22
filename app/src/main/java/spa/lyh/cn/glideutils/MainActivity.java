package spa.lyh.cn.glideutils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import spa.lyh.cn.lib_image.app.ImageLoadUtil;
import spa.lyh.cn.lib_image.app.ProgressInterceptor;
import spa.lyh.cn.lib_image.interceptor.listener.ProgressListener;

public class MainActivity extends AppCompatActivity {
    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574398048769&di=9df5106d0f48f427e61fc16a9d7b1cba&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201605%2F29%2F20160529173820_YtTAu.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressInterceptor.addListener(url, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log.e("progress",progress+"");
            }
        });
        RequestOptions options = new RequestOptions()
                .transform(new RoundedCorners(150));
        ImageLoadUtil.displayImage(this,url,(ImageView) findViewById(R.id.img),options);
    }
}
