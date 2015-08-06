package com.haomi.mantou.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片压缩
 * @author fei
 *
 */
public class PictureCompress {

	private Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    }  
	/**
	 * 图片格式 
	 * @author fei
	 *
	 */
	public static class PictureCompressInfo{
		public int width;
		public int height;
		public String picturePath;
	}
	/**
	 * 压缩图片，返回Bitmap
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getCompressBitmap(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        float hh = 480f;//这里设置高度为  
        float ww = 480f;//这里设置宽度为
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        //return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
        
        return bitmap;
    }
	/**
	 * 压缩图片，返回压缩后的图片路径
	 * @param srcPath
	 * @return
	 */
	public static String getCompressImagePath(String srcPath) {
		String picturePath = null;
		Bitmap bitmap = getCompressBitmap(srcPath);
        
        // 写入压缩后的图片
        File newfile = new File(GouminFileUtil.getEditImageCacheFile());
        try {
            FileOutputStream out=new FileOutputStream(newfile);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)){
                out.flush();
                out.close();
            }
           picturePath = newfile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return picturePath;
    }  
	/**
	 * 得到压缩后的图片 宽，高，路径
	 * @param srcPath
	 * @return
	 */
	public static PictureCompressInfo getCompressImage(String srcPath) {
		PictureCompressInfo newPic = new PictureCompressInfo();
		Bitmap bitmap = getCompressBitmap(srcPath);
        
        // 写入压缩后的图片
        
        File newfile = new File(GouminFileUtil.getEditImageCacheFile());
        try {
            FileOutputStream out=new FileOutputStream(newfile);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)){
                out.flush();
                out.close();
            }
           newPic.picturePath = newfile.getAbsolutePath();
           int[] wh= getBitmapWidthHeight(newPic.picturePath);
           newPic.width = wh[0];
           newPic.height = wh[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return newPic;
    }  
	
	/**
	 * 得到图片的宽和高
	 * @param picturePath
	 * @return
	 */
	public static int[] getBitmapWidthHeight(String picturePath){
		int[] ret = new int[2];
		BitmapFactory.Options options = new BitmapFactory.Options();  
		options.inJustDecodeBounds = true;  
	    BitmapFactory.decodeFile(picturePath, options); 
	    ret[0] = options.outWidth;
	    ret[1] = options.outHeight;
	    return ret;
	}
	/**
	 * 判断指定路径的图片是否有效
	 * @param picturePath
	 * @return
	 */
	public static boolean isValidBitmap(String picturePath){
		BitmapFactory.Options options = new BitmapFactory.Options();  
		options.inJustDecodeBounds = true;  
		BitmapFactory.decodeFile(picturePath, options); 
		
	    if(options.outHeight > 0){
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
	/*
	 * 按实际尺寸来压缩
	 * 
	 * public void te(String imgPath){
		BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = true;
		Bitmap bitmap=BitmapFactory.decodeFile(imgPath,options);
		int bmpWidth = bitmap.getWidth();
		int bmpHeight = bitmap.getHeight();

		// 缩放图片的尺寸
		float scaleWidth = (float) sWidth / bmpWidth; // 按固定大小缩放 sWidth 写多大就多大
		float scaleHeight = (float) sHeight / bmpHeight; //
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		// 产生缩放后的Bitmap对象
		Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth,
				bmpHeight, matrix, false);
		bitmap.recycle();

		// Bitmap to byte[]
		byte[] photoData = bitmap2Bytes(resizeBitmap);

		// save file
		String fileName = "/sdcard/test.jpg";
		FileUtil.writeToFile(fileName, photoData);
	}*/
	
	/*public void writeTofle (String fileName , byte[] data){
		FileUtils.readFileToByteArray(file)
	}*/
	
}
