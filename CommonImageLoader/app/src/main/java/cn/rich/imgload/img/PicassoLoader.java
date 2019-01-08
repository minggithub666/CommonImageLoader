package cn.rich.imgload.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import cn.rich.imgload.img.transfrom.PicassoGrayformation;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;

/**
 * Created by JohnsonFan on 2017/6/27.
 */

public class PicassoLoader implements ILoaderStrategy {
    private volatile static Picasso sPicassoSingleton;
    private final String PICASSO_CACHE = "picasso-cache";
    private static LruCache sLruCache;

    private static Picasso getPicasso(Context mContext) {

        if (sLruCache == null) {
            synchronized (PicassoLoader.class) {
                if (sLruCache == null) {
                    sLruCache = new LruCache(mContext.getApplicationContext());
                }
            }
        }

        if (sPicassoSingleton == null) {
            synchronized (PicassoLoader.class) {
                if (sPicassoSingleton == null) {
                    sPicassoSingleton = new Picasso.Builder(mContext.getApplicationContext()).memoryCache(sLruCache).build();
                }
            }
        }
        return sPicassoSingleton;
    }

    @Override
    public void clearMemoryCache(Context mContext) {
        sLruCache.clear();
    }

    @Override
    public void clearDiskCache(Context mContext) {
        File diskFile = new File(mContext.getCacheDir(), PICASSO_CACHE);
        if (diskFile.exists()) {
            //这边自行写删除代码
//	        FileUtil.deleteFile(diskFile);
        }
    }

    @Override
    public void loadImage(LoaderOptions options, Context mContext) {
        RequestCreator requestCreator = null;
        if (options.url != null) {
            requestCreator = getPicasso(mContext).load(options.url);
        } else if (options.file != null) {
            requestCreator = getPicasso(mContext).load(options.file);
        } else if (options.drawableResId != 0) {
            requestCreator = getPicasso(mContext).load(options.drawableResId);
        } else if (options.uri != null) {
            requestCreator = getPicasso(mContext).load(options.uri);
        }

        if (requestCreator == null) {
            throw new NullPointerException("requestCreator must not be null");
        }
        if (options.targetHeight > 0 && options.targetWidth > 0) {
            requestCreator.resize(options.targetWidth, options.targetHeight);
        }
        if (options.isCenterInside) {
            requestCreator.centerInside();
        } else if (options.isCenterCrop) {
            requestCreator.centerCrop();
        }
        if (options.config != null) {
            requestCreator.config(options.config);

        }
        if (options.errorResId != 0) {
            requestCreator.error(options.errorResId);
        }
        if (options.placeholderResId != 0) {
            requestCreator.placeholder(options.placeholderResId);
        }
        if (options.isGrayfromation) {
            requestCreator.transform(new PicassoGrayformation(mContext, sPicassoSingleton));
        }
        if (options.skipMemoryCache) {
            requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
        }
        if (options.skipDiskCache) {
            requestCreator.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
        }
//		if (options.degrees != 0) {
//			requestCreator.rotate(options.degrees);
//		}

        if (options.targetView instanceof ImageView) {
            requestCreator.into(((ImageView) options.targetView));
        } else if (options.callBack != null) {
            requestCreator.into(new PicassoTarget(options.callBack));
        }
    }

    class PicassoTarget implements Target {
        BitmapCallBack callBack;

        protected PicassoTarget(BitmapCallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if (this.callBack != null) {
                this.callBack.onBitmapLoaded(bitmap);
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            if (this.callBack != null) {
                this.callBack.onBitmapFailed(e);
            }
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }


}
