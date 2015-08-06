package com.haomi.mantou.base.loopj.jsoncache;

import java.io.File;

import com.haomi.mantou.utils.Md5Encode;
import com.haomi.mantou.utils.StringUtil;

/**
 * 缓存策略类 指定缓存生成规则
 * 
 */

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
		return "/sdcard" + File.separator+ Md5Encode.getMD5(requestUrl+page); //Global.CACHE_DIR
	}

	@Override
	// 接口缓存规则 具体指定哪些数据需要缓存
	protected boolean isNeedCache(String requestUrl, int page) {
		
		if (!StringUtil.isEmpty(requestUrl)) {
			
			if(page == 1){
//				if (InterfaceUrlDefine.F_SERVER_RECOMMEND_TAG_TYPE.equals(requestUrl)) {
//					return true;
//				}
			}
			
		}
		return false;
	}

}
