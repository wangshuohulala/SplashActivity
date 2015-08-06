package com.haomi.mantou.base.loopj.requestdata;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.vo.OrderVoData;
import com.haomi.mantou.vo.base.CommonResultVo;

/**
 * 接单
 * @author zhf
 *
 */
public class ReqAcceptOrder extends RequestDataBase{
	
	
	public String order_no;
	
	public ReqAcceptOrder(String orderNO) {
		this.order_no = orderNO;
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_ACCEPT_ORDER;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<CommonResultVo>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		JSONObject js = new JSONObject();
		try {
			js.put("order_no", order_no);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js;
	}

}
