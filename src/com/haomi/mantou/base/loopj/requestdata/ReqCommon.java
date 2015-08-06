package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.vo.base.CommonResultVo;

public class ReqCommon extends RequestDataBase{
	
	private String url;
	public ReqCommon(String url) {
		this.url = url;
	}
	@Override
	public String getRequestUrl() {
		return url;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<CommonResultVo>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		return null;
	}

}
