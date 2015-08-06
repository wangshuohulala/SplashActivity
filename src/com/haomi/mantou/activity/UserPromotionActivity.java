package com.haomi.mantou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.haomi.mantou.R;

public class UserPromotionActivity extends BaseActivity implements View.OnClickListener {

	TextView joinTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_promotion);

		setTitle("我的推广");
		
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
		
		default:
			break;
		}
	}

}
