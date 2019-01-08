package cn.rich.imgload.img;

import android.graphics.Bitmap;

/**
 * 图片加载回调类
 */

public interface BitmapCallBack {

	void onBitmapLoaded(Bitmap bitmap);

	void onBitmapFailed(Exception e);

}
