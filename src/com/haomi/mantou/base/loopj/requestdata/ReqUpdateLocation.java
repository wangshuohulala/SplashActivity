package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.vo.base.CommonResultVo;

public class ReqUpdateLocation extends RequestDataBase{
	
	private String  longitude ;// 经度
	private String  latitude ;//纬度
	
	public ReqUpdateLocation(String longitude, String latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_UPDATE_LOCATION;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<CommonResultVo>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		JSONObject js = new JSONObject();
		try {
			js.put("points", longitude + "," + latitude);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js;
	}

}
