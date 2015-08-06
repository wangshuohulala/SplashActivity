package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haomi.mantou.base.loopj.postjson.RequestPostJsonWrap;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.utils.StringUtil;
import com.haomi.mantou.vo.base.CommonResultVo;
import com.haomi.mantou.vo.base.ResponseBodyBase;

public abstract class RequestDataBase {
	
//	private HashMap<String, String> dataMap;
//	
//	public HashMap<String, String> getDataMap() {
//		return dataMap;
//	}
//	
//	public void setDataMap(HashMap<String, String> dataMap){
//		this.dataMap = dataMap;
//	}
	
	public static final String SUCCESS = InterfaceUrlDefine.SUCCESS+"";
	
	public abstract String getRequestUrl();
	
	public abstract JSONObject getJsonObject();
	
	public abstract Type getVoType();
	
	public StringEntity generateCommonPostEntity(Context cxt){
		return RequestPostJsonWrap.generateCommonPostEntity(null, cxt);
	}
	
	
	public ResponseBodyBase getResponseBodyFromJson(String jsonStr) {
		
		if (!StringUtil.isEmpty(jsonStr)) {
			
			Gson gson = new Gson();
			Type type = getVoType();
			
			if(type != null){
				
				ResponseBodyBase resultBody = gson.fromJson(jsonStr, type);
				
				if (resultBody != null) {
					return resultBody;
				}
			}
		}
		return null;
	}
	
	
	
	public CommonResultVo getResponseBodyFromJson2(String jsonStr) {
		
		if (!StringUtil.isEmpty(jsonStr)) {
			
			Gson gson = new Gson();
			Type type = getVoType();
			
			if(type != null){
				
				//try {
					CommonResultVo resultBody = gson.fromJson(jsonStr, type);
					if (resultBody != null) {
						return resultBody;
					}
//				} catch (JsonSyntaxException e) {
//				}
				
			}
		}
		return null;
	}
}
