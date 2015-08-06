package com.haomi.mantou.base.loopj.requestclient;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

import android.content.Context;

import com.haomi.mantou.utils.sharepreferences.UserInfoPreUtil;

public class RequestPostParams {
	
	private static RequestPostParams requestParamsGenerate;
	
	private UserInfoPreUtil prefUtil;
	
	public RequestPostParams(Context context){
		prefUtil = UserInfoPreUtil.getInstance(context);
	}
	
	public static RequestPostParams getInstance(Context context){
		if(requestParamsGenerate == null){
			requestParamsGenerate = new RequestPostParams(context);
		}
		return requestParamsGenerate;
	}

	public HashMap<String, String> generateParamsMap(){
		
		HashMap<String,String> paramsMap = new HashMap<String,String>();
		
		paramsMap.put(RequestClient.KEY_SEQNUM, getProductId() + getUserId() + System.currentTimeMillis() + getRandom());
		paramsMap.put(RequestClient.KEY_ver, getVer());
		paramsMap.put(RequestClient.KEY_uid, getUserId());
		paramsMap.put(RequestClient.KEY_token, getToken());
		
		return paramsMap;
	}
	
	private String getProductId(){
		return RequestClient.PRODUCT_ID;
	}
	
	private String getVer(){
		return RequestClient.VER;
	}
	
	private String getUserId(){
		return "";
	}
	
	private String getToken(){
		return prefUtil.getSpUserAccessToken();
	}
	
	/**
	 * 取得6位随机数
	 * @return
	 */
	private String getRandom(){
		Random r = new Random();
		int x = r.nextInt(999999);
		DecimalFormat df = new DecimalFormat("000000");
		String str = df.format(x);
		
		return str;
	}
	
}
