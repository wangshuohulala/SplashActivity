package com.haomi.mantou.base.loopj.requestclient;

import java.net.URLEncoder;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.http.HttpEntity;

import android.content.Context;
import android.util.Log;

import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.utils.Des3;
import com.haomi.mantou.utils.Md5Encode;
import com.loopj.android.http.ResponseHandlerInterface;

public class RequestClient {
	
	//---------------------------请求 params 部分
	
	public static final String KEY_SEQNUM = "seqnum";
	public static final String KEY_ver = "ver";
	public static final String KEY_uid = "uid";
	public static final String KEY_token = "token";
	public static final String KEY_data = "data";
	
	public static final String PRODUCT_ID = "GMCAMERAARD";
	public static final String VER = "1.0";
	
	//----------------------------
	
	
	public static void post(Context context, String requestType, HttpEntity entity,
			ResponseHandlerInterface responseHandler) {

		RequestClientFactory.getInstance().post(context,
				getBaseDataInterfaceUrl(requestType), entity, "application/json",
				responseHandler);
	}

	public static void post2(Context context, String url, HttpEntity entity,
			ResponseHandlerInterface responseHandler) {

		RequestClientFactory.getInstance().post(context,url, entity, "application/json", responseHandler);
	}
	
	// 密钥  
    private final static String secretKey = "o4g5b4p1o4g5b4p1o4g5b4p1o4g5b4p1o4g5b4p1";  
    // 向量  
    private final static String iv = "O4G5B4P1";  
    
	public static void get(String url,String params,ResponseHandlerInterface responseHandler){
		
		String reqSignUrl = getSignUrl(url,params);
		//Log.d("GET", reqUrl);
		RequestClientFactory.getInstance().get(reqSignUrl, responseHandler);
	}
	
	public static String getBaseDataInterfaceUrl(String requestType) {
		return InterfaceUrlDefine.BASE_URL +"/"+ requestType;
	}
	
	/**
	 * 生成加密后的url
	 * @param url
	 * @param params
	 * @return
	 */
	public static String getSignUrl(String url,String params){
		Log.d("GET", url+ "  "+params);
		
		String body = "";
		String sign = "";
		try {
			body = URLEncoder.encode(Des3.encode(params), "utf-8");
			
			sign = Md5Encode.getMD5(body+"999");
			
			///Base64.encodeToString(des3EncodeCBC(secretKey, iv, params.getBytes("utf8")), Base64.DEFAULT);
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("GET","encode error");
			
		}
		
		String reqUrl = url+ "?"+"body="+body+"&sign="+sign;
		
		Log.d("GET", reqUrl);
		
		return reqUrl;
	}
	
	/**
     * CBC加密
     * @param key 密钥
     * @param keyiv IV
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {

        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);

        return bOut;
    }
	

}
