package cn.rich.imgload.img;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.view.View;

import java.io.File;

/**
 * 该类为图片加载框架的通用属性封装，不能耦合任何一方的框架
 */
public class LoaderOptions {
    public int placeholderResId;
    public int errorResId;
    public boolean isCenterCrop;
    public boolean isCenterInside;
    public boolean skipMemoryCache; //是否内存缓存
    public boolean skipDiskCache;   //是否磁盘缓存
    public boolean isGrayfromation;   //是否灰化
    public Bitmap.Config config = Bitmap.Config.RGB_565;

    public int targetWidth;
    public int targetHeight;

    public View targetView;//targetView展示图片
    public BitmapCallBack callBack;
    public String url;
    public File file;
    public int drawableResId;
    public Uri uri;

    public ILoaderStrategy loader;//实时切换图片加载库

    public LoaderOptions(String url) {
        this.url = url;
    }

    public LoaderOptions(File file) {
        this.file = file;
    }

    public LoaderOptions(int drawableResId) {
        this.drawableResId = drawableResId;
    }

    public LoaderOptions(Uri uri) {
        this.uri = uri;
    }

    public void into(View targetView) {
        this.targetView = targetView;
        ImageLoader.getInstance().loadOptions(this, targetView.getContext());
    }

    public void bitmap(BitmapCallBack callBack) {
        this.callBack = callBack;
        ImageLoader.getInstance().loadOptions(this, targetView.getContext());
    }

    public LoaderOptions loader(ILoaderStrategy imageLoader) {
        this.loader = imageLoader;
        return this;
    }

    public LoaderOptions placeholder(@DrawableRes int placeholderResId) {
        this.placeholderResId = placeholderResId;
        return this;
    }


    public LoaderOptions error(@DrawableRes int errorResId) {
        this.errorResId = errorResId;
        return this;
    }

    public LoaderOptions centerCrop() {
        isCenterCrop = true;
        return this;
    }

    public LoaderOptions centerInside() {
        isCenterInside = true;
        return this;
    }

    public LoaderOptions config(Bitmap.Config config) {
        this.config = config;
        return this;
    }

    public LoaderOptions resize(int targetWidth, int targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        return this;
    }


    public LoaderOptions skipMemoryCache(boolean skipMemoryCache) {
        this.skipMemoryCache = skipMemoryCache;
        return this;
    }

    public LoaderOptions skipDiskCache(boolean skipDiskCache) {
        this.skipDiskCache = skipDiskCache;
        return this;
    }

    public LoaderOptions isGrayfromation(boolean isGrayfromation) {
        this.isGrayfromation = isGrayfromation;
        return this;
    }


}


