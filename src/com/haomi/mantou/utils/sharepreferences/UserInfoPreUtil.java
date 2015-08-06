package com.haomi.mantou.utils.sharepreferences;

import com.haomi.mantou.vo.UserVo;

import android.content.Context;
import android.content.SharedPreferences;

public class UserInfoPreUtil extends PrefUtilBase {
	
	private static final String DEFAULT_PREF_NAME = "user_preference";
	
	private static final String KEY_TIME = "KEY_TIME";
	private static final String KEY_IS_LOGIN = "KEY_IS_LOGIN";
	private static final String KEY_TOKEN = "KEY_TOKEN"; //加密的 key code，用于向服务器请求数据时使用
	
	private static UserInfoPreUtil instance;
	private static SharedPreferences sp;
	
	public UserInfoPreUtil(Context context, String prefName, int mode){
		sp = context.getSharedPreferences(prefName, mode);
	}

	public static UserInfoPreUtil getInstance(Context context, String prefName, int mode) { 
		
		if(instance == null){
			instance = new UserInfoPreUtil(context, prefName, mode);
		}
		return instance; 
	}
	
	public static UserInfoPreUtil getInstance(Context context, String prefName) {
		return getInstance(context, prefName, Context.MODE_PRIVATE);
	}
	
	public static UserInfoPreUtil getInstance(Context context) {
		return getInstance(context, DEFAULT_PREF_NAME);
	}
	
	@Override
	public SharedPreferences getSp() {
		return sp;
	}
	
	public void setSpUserInfo(UserVo userVo){
		setSpUserAccessToken(userVo.getToken());
	}
	
	/*public UserVo getSpUserInfo(){
		UserVo user = new UserVo();
		user.setUid(getSpUserId());
		user.setUsername(getSpUserName());
		user.setNickname(getSpUserNickName());
		user.setToken(getSpUserAccessToken());
		user.setAvatar(getSpUserAvatar());
		return user;
	}*/
	
	public void clearSpUserInfo(){
		resetStringToQuote(KEY_TOKEN);
		resetStringToQuote(KEY_TIME);
		setIsLogin(false);
	}
	
	
	public void setSpUserAccessToken(String token){
		addString(KEY_TOKEN, token);
	}
	
	public String getSpUserAccessToken(){
		return getStringWithDefaultValueQuote(KEY_TOKEN);
	}
	
	public void setIsLogin(boolean value){
		addBoolean(KEY_IS_LOGIN, value);
	}
	
	public boolean isLogin() {
		/*String token = getSpUserAccessToken();
		if (token != null && token.length() > 0) {
			return true;
		} else {
			return false;
		}*/
		
		return getBoolean(KEY_IS_LOGIN, false);

	}
	
}
