package com.haomi.mantou.base.imgloadconfig;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ImageLoadingConfig {

	public static void displayImage(String url, ImageView imageView,
			int drawableId) {

		ImageLoader.getInstance().displayImage(url, imageView,
				generateDisplayImageOptions(drawableId));

	}

	// 列表图片加载option
	public static DisplayImageOptions generateDisplayImageOptions(int drawableId) {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(drawableId).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		return displayImageOptions;
	}

	// 大图加载option
	public static DisplayImageOptions generateDisplayImageOptions() {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(true).cacheOnDisk(true)
				.cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.build();

		return displayImageOptions;

	}

	// 图片上传部分 图片加载option
	public static DisplayImageOptions generateDisplayImageOptionsNoCatchDisc() {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(false).cacheOnDisk(false)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		return displayImageOptions;
	}
	
	// 大图加载option (供WhisperPublishActivityNew调用)
	public static DisplayImageOptions generateDisplayImageOptionsWithDefaultImg(
			int drawableId) {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(drawableId).resetViewBeforeLoading(true)
				.cacheOnDisk(true).cacheInMemory(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.build();

		return displayImageOptions;

	}
	
	// 大图加载option (供WhisperPublishActivityNew调用)
	public static DisplayImageOptions generateDisplayImageOptionsWithNotClear() {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(false).cacheOnDisk(true)
				.cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.build();

		return displayImageOptions;

	}
	
	//启动图加载option
	public static DisplayImageOptions generateDisplayStartupImageOptions() {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(true).cacheOnDisk(false)
				.cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.build();

		return displayImageOptions;

	}
}
