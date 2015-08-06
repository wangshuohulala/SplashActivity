package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.vo.VerifyCodeVoData;

public class ReqVerifyCode extends RequestDataBase{
	
	private String mobile;
	
	public ReqVerifyCode(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_VERIFY_CODE;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<VerifyCodeVoData>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		JSONObject js = new JSONObject();
		try {
			js.put("mobile", mobile);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js;
	}

}
