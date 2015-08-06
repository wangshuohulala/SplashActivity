package com.haomi.mantou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haomi.mantou.R;

public class WashFinishActivity extends BaseActivity implements View.OnClickListener {

	public static final String KEY_ORDER_ID = "KEY_ORDER_ID";
	private String mOrderId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_wash_finish);

		setTitle("开始洗车");
		mOrderId = getIntent().getStringExtra(KEY_ORDER_ID);
		initView();
		
	}
	
	void initView(){
		//loginBtn = (Button) findViewById(R.id.user_login_login);
		//joinTv = (TextView) findViewById(R.id.user_login_join);
	}

	
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.car_wash_start_ok:
			intent = new Intent(mContext, WashAddPhotoFinishActivity.class);
			intent.putExtra(WashAddPhotoFinishActivity.KEY_ORDER_ID, mOrderId);
			startActivity(intent);
			
			break;
		
		default:
			break;
		}
	}

}
