package com.haomi.mantou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haomi.mantou.R;
import com.haomi.mantou.BdLocationUtil;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrap;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrapDelegateAbstractImpl;
import com.haomi.mantou.base.loopj.requestdata.ReqMainInfo;
import com.haomi.mantou.utils.sharepreferences.UserInfoPreUtil;
import com.haomi.mantou.vo.MainInfoVo;
import com.haomi.mantou.vo.MainInfoVoData;
import com.haomi.mantou.vo.base.CommonResultVo;

public class MainActivity extends BaseActivity implements View.OnClickListener {

	private MainInfoVo mData ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		/*CommonRequestWrap loginWrap = new CommonRequestWrap(
				getApplicationContext(), new RequestDataBrand(),
				new BrandRequestDelegate());
		loginWrap.postRequest();*/
		
		new CommonRequestWrap(this, new ReqMainInfo(), new RequestDelegate()).getRequest();
		
		
	}
	@Override
	protected void onStart() {
		super.onStart();
		BdLocationUtil.getInstance().start();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		BdLocationUtil.getInstance().stop();
	}
	
	void initView(){
		findViewById(R.id.user_center_avatar).setOnClickListener(this);
	}

	private class RequestDelegate extends CommonRequestWrapDelegateAbstractImpl {

		@Override
		public void requestServerSuccess(CommonResultVo resultVo) {

			mData = ((MainInfoVoData) resultVo).getData();
			
			refreshUI();
			
		}

	}
	
	void refreshUI(){
		if(mData != null){
			((TextView)findViewById(R.id.main_info_workno)).setText("工号:"+mData.getWorker_no());
			((TextView)findViewById(R.id.main_info_phone)).setText(mData.getMobile());
			((TextView)findViewById(R.id.main_info_total_order)).setText(mData.getTotalOrder());
			((TextView)findViewById(R.id.main_info_total_money)).setText(mData.getTotalSum());
			((TextView)findViewById(R.id.main_info_wait_order)).setText(mData.getOpenOrder());
			((TextView)findViewById(R.id.main_info_wait_wash)).setText(mData.getAcceptOrder());
			
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.user_center_avatar:
			//intent = new Intent(MainActivity.this, UserLoginActivity.class);
			//startActivity(intent);
			break;
		case R.id.user_center_wait_order:
			intent = new Intent(MainActivity.this, OrderActivity.class);
			intent.putExtra(OrderActivity.KEY_ORDER_TAG, OrderActivity.ORDER_TAG_WAIT_ORDER);
			startActivity(intent);
			break;
		case R.id.user_center_wait_wash:
			intent = new Intent(MainActivity.this, OrderActivity.class);
			intent.putExtra(OrderActivity.KEY_ORDER_TAG, OrderActivity.ORDER_TAG_WAIT_WASH);
			startActivity(intent);
			break;
		case R.id.user_center_all_order:
			intent = new Intent(MainActivity.this, OrderActivity.class);
			intent.putExtra(OrderActivity.KEY_ORDER_TAG, OrderActivity.ORDER_TAG_FINISHED);
			startActivity(intent);
			break;
		case R.id.user_center_income_list:
			intent = new Intent(MainActivity.this, IncomeListActivity.class);
			startActivity(intent);
			break;
		case R.id.user_center_promotion:
			intent = new Intent(MainActivity.this, UserPromotionActivity.class);
			startActivity(intent);
			break;
		case R.id.user_center_logout:
			UserInfoPreUtil.getInstance(mContext).clearSpUserInfo();
			intent = new Intent(MainActivity.this, SplashActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}
	
	private long mkeyTime;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mkeyTime) > 2000) {
				mkeyTime = System.currentTimeMillis();
				Toast.makeText(this, R.string.press_once_again_exit_app,
						Toast.LENGTH_SHORT).show();
			} else {
				finish();
				// System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
