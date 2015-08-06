package com.haomi.mantou;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.haomi.mantou.activity.MainActivity;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MantouApplication extends Application {
	
	private static MantouApplication instance;
	
	private MantouStorageUtil miYouQuanLocalStorageUtil;
	
	
	private List<Activity> mActivityList;

	public MantouStorageUtil getMiYouQuanLocalStorageUtil() {
		return miYouQuanLocalStorageUtil;
	}

	public static MantouApplication getInstance() {
		if (instance == null) {
			instance = new MantouApplication();
		}
		return instance;
	}

	@Override
	public void onCreate() {
		
		super.onCreate();
		
		System.out.println("\r\nGouminTuanApplication oncreate........");
		
		instance = this;
		
		miYouQuanLocalStorageUtil = new MantouStorageUtil();
		miYouQuanLocalStorageUtil.initLocalDir(this);

		initImageLoader(getApplicationContext());
		
		
		mActivityList = new ArrayList<Activity>();
		
	}
	
	public void initImageLoader(Context context) {
		
		File cacheDir = new File(miYouQuanLocalStorageUtil.getImageCacheAbsoluteDir());
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCache(new UnlimitedDiscCache(cacheDir))
//				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}
	
	public List<Activity> getActivityList(){
		return mActivityList;
	}
	
	/**
	 * 回到主页
	 */
	public void goMainActivityfinishOther(){
		for (Activity activity : mActivityList) {
			if(activity != null && !(activity instanceof MainActivity)){
				activity.finish();
			}
		}
	}
	
}
