package com.haomi.mantou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haomi.mantou.R;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrap;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrapDelegateAbstractImpl;
import com.haomi.mantou.base.loopj.requestdata.ReqUserLogin;
import com.haomi.mantou.base.loopj.requestdata.ReqUserRegister;
import com.haomi.mantou.base.loopj.requestdata.ReqVerifyCode;
import com.haomi.mantou.utils.UtilWidget;
import com.haomi.mantou.utils.sharepreferences.UserInfoPreUtil;
import com.haomi.mantou.view.VerificationButton;
import com.haomi.mantou.vo.UserVo;
import com.haomi.mantou.vo.UserVoData;
import com.haomi.mantou.vo.VerifyCodeVo;
import com.haomi.mantou.vo.VerifyCodeVoData;
import com.haomi.mantou.vo.base.CommonResultVo;

public class UserRegisterActivity extends BaseActivity implements View.OnClickListener {

	EditText phoneTv, verificationCodeTv,referrerTv;
	VerificationButton verificationCodeBtn;
	Button loginBtn;
	TextView joinTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_register);

		setTitle("申请加入洗车工");
		
		initView();
		phoneTv.setText("13910109885");
		//phoneTv.setText("13381102000");
		//verificationCodeTv.setText("11111111");
		//String data = "{\"mobile\":\"13381102000\", \"pswd\":\"11111111\"}";
		
	}
	
	void initView(){
		phoneTv = (EditText) findViewById(R.id.user_login_phone);
		verificationCodeTv = (EditText) findViewById(R.id.user_login_verification_code);
		verificationCodeBtn = (VerificationButton) findViewById(R.id.user_login_get_verification_code);
		referrerTv = (EditText) findViewById(R.id.user_login_referrer_phone);
		loginBtn = (Button) findViewById(R.id.user_login_next);
		joinTv = (TextView) findViewById(R.id.user_login_go_login);
		
		verificationCodeBtn.setOnClickListener(this);
		loginBtn.setOnClickListener(this);
		joinTv.setOnClickListener(this);
	}

	private class VerifyRequestDelegate extends CommonRequestWrapDelegateAbstractImpl {

		@Override
		public void requestServerSuccess(CommonResultVo resultVo) {

			VerifyCodeVo result = ((VerifyCodeVoData) resultVo).getData();
			verificationCodeBtn.getVerificationCode(result.getInterval());

			verificationCodeTv.setText(result.getCode());
			Toast.makeText(mContext, resultVo.getMessage(), Toast.LENGTH_LONG)
					.show();

		}

	}

	private class RequestDelegate extends CommonRequestWrapDelegateAbstractImpl {

		@Override
		public void requestServerSuccess(CommonResultVo resultVo) {

			Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
			UserVo user = ((UserVoData) resultVo).getData();
			UserInfoPreUtil.getInstance(mContext).setSpUserAccessToken(
					user.getToken());
			//finish();
			//startActivity(UserRegAddressActivity.class);
			Intent intent = new Intent(mContext, UserRegAddressActivity.class);
			startActivityForResult(intent, 0);
		}

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			finish();
		}
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.user_login_get_verification_code:
			String mobile = phoneTv.getText().toString();
			if(mobile != null && mobile.length() >0 ){
				ReqVerifyCode req1 = new ReqVerifyCode(mobile);
				new CommonRequestWrap(this, req1, new VerifyRequestDelegate()).getRequest();
			}else{
				UtilWidget.showToast(mContext, "请输入手机号");
			}
			break;
		case R.id.user_login_next:
			
			ReqUserRegister req = new ReqUserRegister(phoneTv.getText().toString(), verificationCodeTv.getText().toString(),
					referrerTv.getText().toString());
			new CommonRequestWrap(this, req, new RequestDelegate()).getRequest();
			
			//intent = new Intent(mContext, UserRegAddressActivity.class);
			//startActivity(intent);
			break;
		case R.id.user_login_go_login:
			finish();
			break;
		}
	}
	
}
