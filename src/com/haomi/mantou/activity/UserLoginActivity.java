package com.haomi.mantou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.haomi.mantou.R;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrap;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrapDelegateAbstractImpl;
import com.haomi.mantou.base.loopj.requestdata.ReqUserLogin;
import com.haomi.mantou.base.loopj.requestdata.ReqVerifyCode;
import com.haomi.mantou.utils.UtilWidget;
import com.haomi.mantou.utils.sharepreferences.UserInfoPreUtil;
import com.haomi.mantou.view.VerificationButton;
import com.haomi.mantou.vo.UserVo;
import com.haomi.mantou.vo.UserVoData;
import com.haomi.mantou.vo.VerifyCodeVo;
import com.haomi.mantou.vo.VerifyCodeVoData;
import com.haomi.mantou.vo.base.CommonResultVo;

public class UserLoginActivity extends BaseActivity implements View.OnClickListener {

    TextView phoneTv, verificationCodeTv;
    VerificationButton verificationCodeBtn;
    Button loginBtn;
    TextView joinTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        setTitle("洗车工登录");

        initView();
        phoneTv.setText("13910109885");
        verificationCodeTv.setText("");
        //String data = "{\"mobile\":\"13381102000\", \"pswd\":\"11111111\"}";

    }

    void initView() {
        phoneTv = (TextView) findViewById(R.id.user_login_phone);
        verificationCodeTv = (TextView) findViewById(R.id.user_login_verification_code);
        verificationCodeBtn = (VerificationButton) findViewById(R.id.user_login_get_verification_code);
        loginBtn = (Button) findViewById(R.id.user_login_login);
        joinTv = (TextView) findViewById(R.id.user_login_join);

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
            Toast.makeText(mContext, resultVo.getMessage(), Toast.LENGTH_LONG).show();

        }

    }

    private class RequestDelegate extends CommonRequestWrapDelegateAbstractImpl {

        @Override
        public void requestServerSuccess(CommonResultVo resultVo) {

            Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
            // brandVoList = ((BrandVoData)resultVo).getData();
            UserVo user = ((UserVoData) resultVo).getData();
            UserInfoPreUtil.getInstance(mContext).setSpUserAccessToken(user.getToken());
            UserInfoPreUtil.getInstance(mContext).setIsLogin(true);
            finish();
            startActivity(MainActivity.class);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.user_login_get_verification_code:
                String mobile = phoneTv.getText().toString();
                if (mobile != null && mobile.length() > 0) {
                    ReqVerifyCode req1 = new ReqVerifyCode(mobile);
                    new CommonRequestWrap(this, req1, new VerifyRequestDelegate()).getRequest();
                } else {
                    UtilWidget.showToast(mContext, "请输入手机号");
                }
                break;
            case R.id.user_login_login:
                ReqUserLogin req = new ReqUserLogin(phoneTv.getText().toString(), verificationCodeTv.getText().toString());
                new CommonRequestWrap(this, req, new RequestDelegate()).getRequest();
                break;
            case R.id.user_login_join:
                intent = new Intent(mContext, UserRegisterActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

}
