package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.vo.UserVoData;

public class ReqUserRegister extends RequestDataBase{
	
	public String mobile;
	public String verifycode;
	public String recommend_mobile;
	
	public ReqUserRegister(String mobile, String verifycode, String recommentMobile) {
		this.mobile = mobile;
		this.verifycode = verifycode;
		this.recommend_mobile = recommentMobile;
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_REGISTER;
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
			js.put("recommend_mobile", recommend_mobile);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js;
	}

}
