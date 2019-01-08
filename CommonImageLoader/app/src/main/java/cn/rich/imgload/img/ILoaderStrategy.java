package cn.rich.imgload.img;

import android.content.Context;

/**
 * 加载策略类
 */

public interface ILoaderStrategy {

	void loadImage(LoaderOptions options, Context mContext);

	/**
	 * 清理内存缓存
	 */
	void clearMemoryCache(Context mContext);

	/**
	 * 清理磁盘缓存
	 */
	void clearDiskCache(Context mContext);

}
