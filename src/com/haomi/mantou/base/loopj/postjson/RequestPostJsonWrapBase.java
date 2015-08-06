package com.haomi.mantou.base.loopj.postjson;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonObject;

public class RequestPostJsonWrapBase {

	/**
	 * 将data和其他pramas转换成jsonObject
	 * 
	 * @param dataMap
	 * @param paramsMap
	 * @return
	 */
	protected static JsonObject generateRequestJsonOjbect(
			HashMap<String, String> dataMap, HashMap<String, String> paramsMap) {
		
		JsonObject requestJsonOjbect = new JsonObject();
		JsonObject dataJsonObject;
		
		if(dataMap != null && dataMap.size() > 0){
			dataJsonObject = new JsonObject();
			for (Entry<String, String> entry : dataMap.entrySet()) {
				dataJsonObject.addProperty(entry.getKey(), entry.getValue());
			}
			requestJsonOjbect.add("data", dataJsonObject);
		}

		if (paramsMap != null && paramsMap.size() > 0) {
			for (Entry<String, String> entry : paramsMap.entrySet()) {
				requestJsonOjbect.addProperty(entry.getKey(), entry.getValue());
			}
		}
		return requestJsonOjbect;
	}

}
