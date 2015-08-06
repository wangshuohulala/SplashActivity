package com.haomi.mantou.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;


public class GouminFileUtil {
	/**
	 * 根目录
	 */
	public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Mantou/";
	/**
	 * 图片下载路径
	 */
	public static final String IMAGE_PATH = ROOT_PATH +"/picture/";
	
	/**
	 * 图片缓存路径
	 */
	public static final String CACHE_PATH = ROOT_PATH+"/Cache/";
	/**
	 * 处理图片时的临时路径
	 */
	public static final String EDIT_IMAGE_CACHE_PATH = ROOT_PATH+"/EditImageCache/";
	/**
	 * 缓存数据的路径
	 */
	public static final String CACHE_DATA_PATH = ROOT_PATH+"/CacheData/";
	
	/**
	 * 得到一个图片文件名
	 * @return
	 */
	public static String getImageName(){
		return  System.currentTimeMillis()+".jpg";
	}
	
	public static void initFirstPath(){
		File dirFirstFile = new File(ROOT_PATH);//新建一级主目录  
		  
        if(!dirFirstFile.exists()){//判断文件夹目录是否存在  
 
             dirFirstFile.mkdir();//如果不存在则创建  
        }
        dirFirstFile = null;

	}
	
	/**
     * 保存图片的地址
     * @return
     */
	public static String getDownloadPictureFile(){
		initFirstPath();
		String fileName = getImageName();
		File file = new File(IMAGE_PATH);
		if(!file.exists()){
			file.mkdir();
		}
		return IMAGE_PATH + fileName;
	}
	
	/**
     * 得到一个临时存放编辑图片的地址
     * @return
     */
	public static String getEditImageCacheFile(){
		initFirstPath();
		String fileName = getImageName();
		File file = new File(EDIT_IMAGE_CACHE_PATH);
		if(!file.exists()){
			file.mkdir();
		}
		return EDIT_IMAGE_CACHE_PATH + fileName;
	}
	
	 /**
     * 将 bitmap写到 file
     * @param bitmap
     * @param filePath
     */
    public static void writeBitmapToFile(Bitmap bitmap, String filePath){
		 // 写入压缩后的图片
       File tmpfile=new File(filePath);
       tmpfile.getName();
       File newfile = new File(filePath);
       try {
           FileOutputStream out=new FileOutputStream(newfile);
           if(bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)){
               out.flush();
               out.close();
           }
          
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       
	}
    
    
    /** 
     * 删除单个文件 
     * @param   sPath    被删除文件的文件名 
     * @return 单个文件删除成功返回true，否则返回false 
     */  
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
	
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除编辑照片时的临时文件
	 * @return
	 */
	public static boolean deleteGouminEditPicDir(){
		return deleteDirectory(EDIT_IMAGE_CACHE_PATH);
	}
}
