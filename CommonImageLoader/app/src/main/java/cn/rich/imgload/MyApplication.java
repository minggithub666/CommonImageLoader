package cn.rich.imgload;

import android.app.Application;
import cn.rich.imgload.img.GlideLoader;
import cn.rich.imgload.img.ImageLoader;
import cn.rich.imgload.img.PicassoLoader;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化图片库
        //ImageLoader.getInstance().setGlobalImageLoader(new PicassoLoader());
        ImageLoader.getInstance().setGlobalImageLoader(new GlideLoader());
    }

}
