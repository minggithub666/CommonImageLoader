package cn.rich.imgload.img;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import cn.rich.imgload.img.transfrom.GlideGrayTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class GlideLoader implements ILoaderStrategy {


    public void loadImage(final LoaderOptions options, Context mContext) {
        RequestOptions glideOptions = new RequestOptions();

        if (options.errorResId != 0) {
            glideOptions.error(options.errorResId);
        }
        if (options.placeholderResId != 0) {
            glideOptions.placeholder(options.placeholderResId);
        }
        if (options.targetHeight > 0 && options.targetWidth > 0) {
            glideOptions.override(options.targetWidth, options.targetHeight);
        }
        if (options.skipMemoryCache) {
            glideOptions.skipMemoryCache(true);
        }
        if (options.skipDiskCache) {
            glideOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        }

        //glideOptions.format(DecodeFormat.PREFER_ARGB_8888);

        if (options.isCenterInside) {
            glideOptions.centerInside();
        } else if (options.isCenterCrop) {
            glideOptions.centerCrop();

        }
        if (options.isGrayfromation) {
            glideOptions.transform(new GlideGrayTransformation());

        }

        RequestBuilder<Drawable> mRequestBuilder = null;
        if (options.url != null) {
            mRequestBuilder = Glide.with(mContext).load(options.url);
        } else if (options.file != null) {
            mRequestBuilder = Glide.with(mContext).load(options.file);
        } else if (options.drawableResId != 0) {
            mRequestBuilder = Glide.with(mContext).load(options.drawableResId);
        } else if (options.uri != null) {
            mRequestBuilder = Glide.with(mContext).load(options.uri);
        }
        if (options.callBack != null) {
            mRequestBuilder.addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    options.callBack.onBitmapFailed(e);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    options.callBack.onBitmapLoaded(((BitmapDrawable) resource).getBitmap());
                    return false;
                }
            });
        }
        if (options.targetView instanceof ImageView) {
            mRequestBuilder
                    .apply(glideOptions)
                    .into(((ImageView) options.targetView));
        }

    }

    /**
     * 清理内存缓存
     */
    public void clearMemoryCache(Context mContext) {
        Glide.get(mContext.getApplicationContext()).clearMemory();
    }

    /**
     * 清理磁盘缓存
     */
    public void clearDiskCache(Context mContext) {
        Glide.get(mContext.getApplicationContext()).clearDiskCache();
    }


}
