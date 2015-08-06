package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.vo.WorkStatusVoData;

public class ReqWorkStatus extends RequestDataBase{
	
	
	public ReqWorkStatus() {
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_WORK_STATUS;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<WorkStatusVoData>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		return null;
	}

}
