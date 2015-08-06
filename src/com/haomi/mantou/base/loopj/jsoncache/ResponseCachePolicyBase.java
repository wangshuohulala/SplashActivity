package com.haomi.mantou.base.loopj.jsoncache;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * 
 * 缓存策略类基类 负责生成文件地址，存储和获取缓存数据
 * 
 * 
 *  集成子类模板 用于指定缓存文件命名规则和指定那些数据接口需要缓存
	public class ResponseCachePolicy extends ResponseCachePolicyBase {
	
		private static ResponseCachePolicy responseCachePolicy;
	
		public static ResponseCachePolicy getInstance() {
			if (responseCachePolicy == null) {
				responseCachePolicy = new ResponseCachePolicy();
			}
			return responseCachePolicy;
		}
		
		//本地缓存文件规则
		@Override
		protected String getCacheFilePath(String requestUrl, int page){
			return Global.CACHE_DIR + File.separator+ Md5Encode.getMD5(requestUrl+page);
		}
	
		// 接口缓存规则 具体指定哪些数据需要缓存
		@Override
		public boolean isNeedCache(String requestUrl, int page) {
			if (!StringUtil.isEmpty(requestUrl)) {
				// 发现 热门接口(作为列子)
				if (InterfaceUrlDefine.F_SERVER_WHISPER_NEW_TYPE.equals(requestUrl)) {
					if (page == 1) {
						return true;
					}
				}
			}
			return false;
		}
	
	}
 * 
 * 
 */

public class ResponseCachePolicyBase {
	
	protected String getCacheFilePath(String requestUrl, int page){
		return null;
	}
	
	protected boolean isNeedCache(String requestUrl, int page){
		return false;
	}
	
	public void cacheResponseData(String requestUrl, int page,
			byte[] responseBytes) {

		if (isNeedCache(requestUrl, page)) {

			String filePath = getCacheFilePath(requestUrl, page);

			cacheDataToSdcard(filePath, responseBytes);
		}
	}

	public String getResponseCacheData(String requestUrl, int page) {

		if (isNeedCache(requestUrl, page)) {

			String filePath = getCacheFilePath(requestUrl, page);

			return getCacheDataFromSdcard(filePath);
		}

		return null;

	}
	
	//缓存数据至本地
	protected void cacheDataToSdcard(String filePath, byte[] cacheData) {
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			bos.write(cacheData);
			bos.flush();
			bos.close();
//			System.out.println("\r\n write cache : " + filePath);
//			System.out.println("\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取本地缓存数据
	protected String getCacheDataFromSdcard(String filePath) {

		InputStreamReader isr;
		try {
			isr = new InputStreamReader(new FileInputStream(new File(filePath)));
			BufferedReader br = new BufferedReader(isr);
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			br.close();

//			System.out.println("\r\nread from cache ....");
//			System.out.println("\r\n");

			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
