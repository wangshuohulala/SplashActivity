package com.haomi.mantou.base.loopj.requestclient;


import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.haomi.mantou.MantouApplication;
import com.haomi.mantou.base.loopj.requestdata.RequestDataBase;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.utils.StringUtil;
import com.haomi.mantou.utils.sharepreferences.UserInfoPreUtil;
import com.haomi.mantou.vo.base.CommonResultVo;

/**
 * 上传图片服务对象
 * @author Jess
 * 
 */
public class UploadImagesService {
	
	//private String uploadImgUrl = InterfaceUrlDefine.URL_BEGIN_ORDER;
	
	private static UploadImagesService sInstance = null;

	public static UploadImagesService getInstance() {
		if (sInstance == null) {
			sInstance = new UploadImagesService();
		}
		return sInstance;
	}

	public CommonResultVo uploadImage(RequestDataBase requestData, HashMap<String, String> images ) {
		
		Log.i("GET", "uploadImage2 ---------------");
		String responseString = "";
		boolean imgSuccess = false;
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		httpParams.setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);

		HttpContext httpContext = new BasicHttpContext();
		
		String signUrl = RequestClient.getSignUrl(requestData.getRequestUrl(), requestData.getJsonObject().toString());
		Log.i("GET", signUrl);
		HttpPost httpPost = new HttpPost(signUrl);
		//httpPost.setHeader("User-Agent", NetConstant.USER_AGENT);

		try {
			MultipartEntity multipartContent = new MultipartEntity();

			/*Map<String, String> map = new TreeMap<String, String>();
			map.put("order_no", orderid);
			map.put("token", UserInfoPreUtil.getInstance(MantouApplication.getInstance()).getSpUserAccessToken());
			for (Map.Entry<String, String> entry : map.entrySet()) {
				multipartContent.addPart(entry.getKey(),
						new StringBody(entry.getValue()));
			}*/

			if (images != null) {
				for (Entry<String, String> entrySet : images.entrySet()) {
					multipartContent.addPart(entrySet.getKey(), new FileBody(
							new File(entrySet.getValue()), "image/jpeg"));
				}
				/*File imageFile = new File(srcPath);
				if (imageFile == null || !imageFile.exists())
					return null;*/

				/*multipartContent.addPart("images[0]", new FileBody(
						new File(srcPath), "image/jpeg"));
				multipartContent.addPart("images[1]", new FileBody(
						new File(srcPath), "image/jpeg"));*/
				
				/*multipartContent.addPart("image", new FileBody(
						new File(srcPath), "image/jpeg"));*/
				
			}
			
			Log.i("GET", httpPost.toString());
			httpPost.setEntity(multipartContent);
			HttpResponse response = httpClient.execute(httpPost, httpContext);
			
			//RequestClientFactory.getInstance().post(null, null, multipartContent, "", null);

			responseString = EntityUtils.toString(response.getEntity());
			imgSuccess = true;
		} catch (Exception e) {
			imgSuccess = false;
			e.printStackTrace();
		}

		Log.i("GET", responseString);
		
		if (imgSuccess) {
			if (!StringUtil.isEmpty(responseString)) {

				try {
					CommonResultVo resultVo = requestData.getResponseBodyFromJson2(responseString);
					if (resultVo != null) {
						return resultVo;
					}
				} catch (JsonSyntaxException e) {
				}

			}
			
		}
		
		CommonResultVo resultVo = new CommonResultVo();
		resultVo.setCode("101");
		resultVo.setMessage("数据解析失败");
		return resultVo;
	}
}
