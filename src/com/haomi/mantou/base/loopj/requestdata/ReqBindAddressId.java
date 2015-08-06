package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.MantouApplication;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.utils.sharepreferences.UserInfoPreUtil;
import com.haomi.mantou.vo.base.CommonResultVo;

/**
 * 添加 地区，身份证照片
 * @author zhf
 *
 */
public class ReqBindAddressId extends RequestDataBase{
	
	public String post_code;
	public String address;
	
	public ReqBindAddressId(String post_code, String verifycode) {
		this.post_code = post_code;
		this.address = verifycode;
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_BIND_ADDRESS_ID;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<CommonResultVo>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		JSONObject js = new JSONObject();
		try {
			js.put("post_code", post_code);
			js.put("address", address);
			js.put("token", UserInfoPreUtil.getInstance(MantouApplication.getInstance()).getSpUserAccessToken());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js;
	}

}
