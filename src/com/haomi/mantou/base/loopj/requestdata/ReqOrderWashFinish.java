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
 * 完成洗车  上传洗车后照片
 * @author zhf
 *
 */
public class ReqOrderWashFinish extends RequestDataBase{
	
	private String orderId;
	
	public ReqOrderWashFinish(String orderId) {
		this.orderId = orderId;
	}
	@Override
	public String getRequestUrl() {
		return InterfaceUrlDefine.URL_FINISH_ORDER;
	}

	@Override
	public Type getVoType() {
		return new TypeToken<CommonResultVo>() {}.getType();
	}

	@Override
	public JSONObject getJsonObject() {
		JSONObject js = new JSONObject();
		try {
			js.put("order_no", orderId);
			js.put("token", UserInfoPreUtil.getInstance(MantouApplication.getInstance()).getSpUserAccessToken());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js;
	}

}
