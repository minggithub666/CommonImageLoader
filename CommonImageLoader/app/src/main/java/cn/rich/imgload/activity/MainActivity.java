package cn.rich.imgload.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import cn.rich.imgload.R;
import cn.rich.imgload.img.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        imageView = (ImageView) findViewById(R.id.image_view);


    }

    public void loadImage(View view) {
        loadImg();
    }




    private void loadImg()
    {
        //使用方式
        String url = "http://ww2.sinaimg.cn/large/7a8aed7bgw1eutsd0pgiwj20go0p0djn.jpg";
        ImageLoader.getInstance()
                .load(url)
                .isGrayfromation(true)
                .resize(400, 600)
                .centerCrop()
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
