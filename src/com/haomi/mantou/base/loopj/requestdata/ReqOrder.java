package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.vo.OrderVoData;

public class ReqOrder extends RequestDataBase{
	
	public static final int STATUS_OPEN = 1; // "待接单",
	public static final int STATUS_ACCEPT = 2;// 清待洗
	public static final int STATUS_BEGIN = 3;// 开工
	public static final int STATUS_FINISH = 4;// 完工
	public static final int STATUS_CLOSE = 5;// 已完成
	public static final int STATUS_CANCEL = 6;// 取消
	public static final int STATUS_EXCEPTION = 7;// 异常
	
	
	public int page = 1;
	public int ordertag = 1; //1是待接单，2是待清洗，3是已完成。
	
	public ReqOrder() {
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_ORDER_LIST;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<OrderVoData>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		JSONObject js = new JSONObject();
		try {
			js.put("page", page);
			js.put("ordertag", ordertag);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js;
	}

}
