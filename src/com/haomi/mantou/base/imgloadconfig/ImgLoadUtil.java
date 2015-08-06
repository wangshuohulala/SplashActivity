package com.haomi.mantou.base.imgloadconfig;

import android.widget.ImageView;

import com.haomi.mantou.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImgLoadUtil {
	
	private static final DisplayImageOptions commonDisplayOptions = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.ic_launcher);
	
	public static void displayImage(String imgUrl, ImageView imgView){
		
		ImageLoader.getInstance().displayImage(imgUrl, imgView, commonDisplayOptions);
		
	}

}
