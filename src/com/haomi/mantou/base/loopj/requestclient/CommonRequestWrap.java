package com.haomi.mantou.base.loopj.requestclient;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.haomi.mantou.MantouApplication;
import com.haomi.mantou.base.loopj.requestdata.RequestDataBase;
import com.haomi.mantou.utils.StringUtil;
import com.haomi.mantou.utils.sharepreferences.UserInfoPreUtil;
import com.haomi.mantou.vo.base.CommonResultListVo;
import com.haomi.mantou.vo.base.CommonResultVo;
import com.haomi.mantou.vo.base.ResponseBodyBase;
import com.loopj.android.http.handler.TextHttpResponseHandler;

public class CommonRequestWrap {
	
	public interface CommonRequestWrapDelegate{
		
		/**
		 * 请求服务器开始
		 * @param progressDialog
		 */
		public void requestServerStart(ProgressDialog progressDialog);
		
		/**
		 * 请求服务器返回成功数据
		 * @param resultVo
		 */
		public void requestServerSuccess(CommonResultVo resultVo);
		
		/**
		 * 请求服务器返回失败数据
		 * @param cxt
		 * @param resultCode
		 * @param errorMsg
		 */
		public void requestServerFailure(Context cxt, String resultCode, String errorMsg);
		
		/**
		 * 请求服务器返回网络连接异常
		 */
		public void requestServerNetWorkError();
		
		/**
		 * 请求服务器响应异常(无具体异常信息)
		 * @param cxt
		 */
		public void requestServerResponseError(Context cxt);
		
		/**
		 * 请求服务器结束
		 * @param progressDialog
		 */
		public void requestServerEnd(ProgressDialog progressDialog);
		
	}
	
	private Context cxt;
	private CommonRequestWrapDelegate commonRequestWrapDelegate;
	private ProgressDialog progressDialog;
	
	private RequestDataBase requestData;
	
	public CommonRequestWrap(Context cxt, RequestDataBase requestData,
			CommonRequestWrapDelegate commonRequestWrapDelegate) {
		this.cxt = cxt;
		this.requestData = requestData;
		this.commonRequestWrapDelegate = commonRequestWrapDelegate;
	}

	public CommonRequestWrap(Context cxt, RequestDataBase requestData, int stringId,
			CommonRequestWrapDelegate commonRequestWrapDelegate) {
		this.cxt = cxt;
		this.requestData = requestData;
		this.commonRequestWrapDelegate = commonRequestWrapDelegate;

		progressDialog = RequestProgressDialogWrap.createProgressDialog(cxt,
				stringId);
	}
	
	public CommonRequestWrap(Context cxt,CommonRequestWrapDelegate commonRequestWrapDelegate){
		this.cxt = cxt;
		this.commonRequestWrapDelegate = commonRequestWrapDelegate;
	}
	
	public CommonRequestWrap(CommonRequestWrapDelegate commonRequestWrapDelegate){
		this.cxt = MantouApplication.getInstance();
		this.commonRequestWrapDelegate = commonRequestWrapDelegate;
	}
	public void postRequest(){
		
		StringEntity stringEntity = requestData.generateCommonPostEntity(cxt);
		
		if(stringEntity == null){
			return;
		}
		
		RequestClient.post(cxt, requestData.getRequestUrl(),  stringEntity , new TextHttpResponseHandler() {
			
			@Override
			public void onStart() {
				commonRequestWrapDelegate.requestServerStart(progressDialog);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				commonRequestWrapDelegate.requestServerNetWorkError();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				
				if(!StringUtil.isEmpty(responseString)){
					
					//responseString = responseString.replace("for (;;);", "").replace("end;;;", "");
					
//					System.out.println("\r\n\r\n CommonRequest Response : " + responseString);
					
					ResponseBodyBase responseBody = requestData.getResponseBodyFromJson(responseString);
					
					if(responseBody != null){
						CommonResultListVo resultVo = responseBody.getResult();
						
						if(resultVo != null){
							
							String resultCode = resultVo.getCode();
							
							if(RequestDataBase.SUCCESS.equals(resultCode)){ //先判断返回结果
								commonRequestWrapDelegate.requestServerSuccess(resultVo);
							}else{
								commonRequestWrapDelegate.requestServerFailure(cxt, resultCode, resultVo.getMessage());
							}
							return;
						}
					}
					
				}
				
				commonRequestWrapDelegate.requestServerResponseError(cxt);
			}
			
			@Override
			public void onFinish() {
				commonRequestWrapDelegate.requestServerEnd(progressDialog);
			}
		});
		
	}
	
	public void getRequest(){
		JSONObject js = requestData.getJsonObject();
		if(js == null){//为空 需要 {}
			js = new JSONObject();
		}
		//统一加token字段
		try {
			if(UserInfoPreUtil.getInstance(cxt).isLogin()){
				js.put("token", UserInfoPreUtil.getInstance(cxt).getSpUserAccessToken());
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		//请求
		RequestClient.get(requestData.getRequestUrl(), js.toString(), new TextHttpResponseHandler() {
			
			@Override
			public void onStart() {
				commonRequestWrapDelegate.requestServerStart(progressDialog);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				commonRequestWrapDelegate.requestServerNetWorkError();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Log.i("GET",responseString);
				try {

					if (!StringUtil.isEmpty(responseString)) {

						CommonResultVo resultVo = requestData
								.getResponseBodyFromJson2(responseString);

						if (resultVo != null) {

							String resultCode = resultVo.getCode();

							if (RequestDataBase.SUCCESS.equals(resultCode)) { // 先判断返回结果
								commonRequestWrapDelegate
										.requestServerSuccess(resultVo);
							} else {
								commonRequestWrapDelegate.requestServerFailure(
										cxt, resultCode, resultVo.getMessage());
							}
							return;
						}

					}

					commonRequestWrapDelegate.requestServerResponseError(cxt);

				} catch (Exception e) {
					e.printStackTrace();
					commonRequestWrapDelegate.requestServerResponseError(cxt);
				}
			}

			@Override
			public void onFinish() {
				commonRequestWrapDelegate.requestServerEnd(progressDialog);
			}
		});
		
	}
	
	
	public void postImage(HashMap<String, String> images){
		JSONObject js = requestData.getJsonObject();
		if(js == null){//为空 需要 {}
			js = new JSONObject();
		}
		//统一加token字段
		try {
			if(UserInfoPreUtil.getInstance(cxt).isLogin()){
				js.put("token", UserInfoPreUtil.getInstance(cxt).getSpUserAccessToken());
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String urlSign = RequestClient.getSignUrl(requestData.getRequestUrl(), requestData.getJsonObject().toString());
		//请求
		
		MultipartEntity multipartContent = new MultipartEntity();
		if (images != null) {
			for (Entry<String, String> entrySet : images.entrySet()) {
				multipartContent.addPart(entrySet.getKey(), new FileBody(
						new File(entrySet.getValue()), "image/jpeg"));
			}
			
		}
		RequestClient.post(cxt, urlSign, multipartContent,  new TextHttpResponseHandler() {
			
			@Override
			public void onStart() {
				commonRequestWrapDelegate.requestServerStart(progressDialog);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				commonRequestWrapDelegate.requestServerNetWorkError();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Log.i("GET",responseString);
				try {

					if (!StringUtil.isEmpty(responseString)) {

						CommonResultVo resultVo = requestData
								.getResponseBodyFromJson2(responseString);

						if (resultVo != null) {

							String resultCode = resultVo.getCode();

							if (RequestDataBase.SUCCESS.equals(resultCode)) { // 先判断返回结果
								commonRequestWrapDelegate
										.requestServerSuccess(resultVo);
							} else {
								commonRequestWrapDelegate.requestServerFailure(
										cxt, resultCode, resultVo.getMessage());
							}
							return;
						}

					}

					commonRequestWrapDelegate.requestServerResponseError(cxt);

				} catch (Exception e) {
					e.printStackTrace();
					commonRequestWrapDelegate.requestServerResponseError(cxt);
				}
			}

			@Override
			public void onFinish() {
				commonRequestWrapDelegate.requestServerEnd(progressDialog);
			}
		});
				
	}
	
}
