package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.vo.UserVoData;

public class ReqUserLogin extends RequestDataBase{
	
	public String mobile;
	public String verifycode;
	
	public ReqUserLogin(String mobile, String verifycode) {
		this.mobile = mobile;
		this.verifycode = verifycode;
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_LOGIN;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<UserVoData>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		JSONObject js = new JSONObject();
		try {
			js.put("mobile", mobile);
			js.put("verifycode", verifycode);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js;
	}

}
