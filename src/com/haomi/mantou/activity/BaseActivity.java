package com.haomi.mantou.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.haomi.mantou.R;
import com.haomi.mantou.MantouApplication;

public class BaseActivity extends Activity{
	
	Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		MantouApplication.getInstance().getActivityList().add(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MantouApplication.getInstance().getActivityList().remove(this);
	}
	
	public void titleBarBack(View v){
		finish();
	}
	protected void setTitle(String title) {
		TextView titleTv = (TextView) findViewById(R.id.titlebar_title);
		titleTv.setText(title);
	}
	
	protected void startActivity(Class<?> cls){
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}
}
