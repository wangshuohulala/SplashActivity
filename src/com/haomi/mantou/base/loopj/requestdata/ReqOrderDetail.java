package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.vo.OrderDetailVoData;

public class ReqOrderDetail extends RequestDataBase{
	
	private String orderId;
	public ReqOrderDetail(String orderid) {
		this.orderId = orderid;
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_ORDER_DETAIL;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<OrderDetailVoData>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		JSONObject js = new JSONObject();
		try {
			js.put("order_no", orderId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js;
	}

}
