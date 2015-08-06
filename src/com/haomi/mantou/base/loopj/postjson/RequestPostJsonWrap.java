package com.haomi.mantou.base.loopj.postjson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import android.content.Context;

import com.google.gson.JsonObject;
import com.haomi.mantou.base.loopj.requestclient.RequestPostParams;

public class RequestPostJsonWrap extends RequestPostJsonWrapBase {

	/**
	 * 封装请求参数生成 StringEntity
	 * 
	 * @param dataMap
	 * @param pramasMap
	 * @return
	 */
	public static StringEntity generateCommonPostEntity(
			HashMap<String, String> dataMap, Context cxt) {
		
		HashMap<String, String> pramasMap = RequestPostParams.getInstance(cxt).generateParamsMap();

		JsonObject postBody = generateRequestJsonOjbect(dataMap, pramasMap);

		System.out.println("\r\npostBody : " + postBody.toString());

		return generateRequestEntity(postBody);
	}

	/**
	 * 将postjson 装换为 StringEntity
	 * 
	 * @param postJson
	 * @return
	 */
	private static StringEntity generateRequestEntity(JsonObject postJson) {

		// params is a JSONObject
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(postJson.toString(), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json"));

		return stringEntity;

	}

}
