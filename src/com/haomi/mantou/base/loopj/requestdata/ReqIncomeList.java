package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.vo.IncomeListVoData;

public class ReqIncomeList extends RequestDataBase{
	
	public int page = 1;
	
	public ReqIncomeList() {
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_INCOME_LIST;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<IncomeListVoData>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		JSONObject js = new JSONObject();
		try {
			js.put("page", page);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js;
	}

}
