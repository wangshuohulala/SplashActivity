package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.MantouApplication;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.utils.sharepreferences.UserInfoPreUtil;
import com.haomi.mantou.vo.PostcodeMapVoData;
import com.haomi.mantou.vo.UserVoData;
import com.haomi.mantou.vo.VerifyCodeVoData;

/**
 * 地区列表
 * @author zhf
 *
 */
public class ReqPostCode extends RequestDataBase{
	
	
	public ReqPostCode() {
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_GET_POSTCODE;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<PostcodeMapVoData>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		JSONObject js = new JSONObject();
		try {
			js.put("token", UserInfoPreUtil.getInstance(MantouApplication.getInstance()).getSpUserAccessToken());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js;
	}

}
