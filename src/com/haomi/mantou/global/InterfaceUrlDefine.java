package com.haomi.mantou.global;

public class InterfaceUrlDefine {
	
	/**
	 * 接口版本号
	 */
	public static final String API_VERSION = "v2";
	
	/**
	 * 正式服务器地址
	 */
	public static final String OFFICIAL_URL = "http://mallapi.goumin.com/" ;

	public static final String BASE_URL = "http://www.mt885.com/api"; //"http://mantou.stargather.cn/api";
	
	public static final int SUCCESS = 100; // CODE 
	
	//验证码
	public static final String URL_VERIFY_CODE = BASE_URL + "/get-verify-code";
	//登录
	public static final String URL_LOGIN = BASE_URL + "/login-worker";
	//注册
	public static final String URL_REGISTER = BASE_URL + "/register-worker";
	//省，市列表
	public static final String URL_GET_POSTCODE = BASE_URL + "/get-postcode-map";
	//绑定地址, 身份证
	public static final String URL_BIND_ADDRESS_ID = BASE_URL + "/apply-register";
	//主页信息
	public static final String URL_MAIN_INFO = BASE_URL + "/get-worker";
	//订单列表
	public static final String URL_ORDER_LIST = BASE_URL + "/get-orders";
	//订单详情
	public static final String URL_ORDER_DETAIL = BASE_URL + "/get-order";
	//上班状态
	public static final String URL_WORK_STATUS = BASE_URL + "/get-worker-status";
	//上班
	public static final String URL_PUNCH_IN = BASE_URL + "/punch-in";
	//下班
	public static final String URL_PUNCH_OUT = BASE_URL + "/punch-out";
	//接单
	public static final String URL_ACCEPT_ORDER = BASE_URL + "/accept-order";
	//取消订单
	public static final String URL_CANCEL_ORDER = BASE_URL + "/cancel-order";
	//洗车工开工
	public static final String URL_BEGIN_ORDER = BASE_URL + "/begin-order";
	//洗车工完工
	public static final String URL_FINISH_ORDER = BASE_URL + "/finish-order";
	//收入明细
	public static final String URL_INCOME_LIST = BASE_URL + "/get-worker-overview";
	//更新洗车工位置
	public static final String URL_UPDATE_LOCATION = BASE_URL + "/update-worker-location";
	
	
}
