package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.vo.MainInfoVoData;

public class ReqMainInfo extends RequestDataBase{
	
	
	public ReqMainInfo() {
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_MAIN_INFO;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<MainInfoVoData>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		
		return null;
	}

}
