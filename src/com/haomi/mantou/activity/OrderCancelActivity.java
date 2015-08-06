package com.haomi.mantou.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.haomi.mantou.R;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrap;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrapDelegateAbstractImpl;
import com.haomi.mantou.base.loopj.requestdata.ReqAcceptOrder;
import com.haomi.mantou.base.loopj.requestdata.ReqCancleOrder;
import com.haomi.mantou.utils.UtilWidget;
import com.haomi.mantou.vo.base.CommonResultVo;

public class OrderCancelActivity extends BaseActivity {

	private Button okBtn;
	private EditText reasonEt;
	
	public static final String KEY_ORDER_ID = "KEY_ORDER_ID";
	private String mOrderId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_cancel);

		setTitle("取消接单");
		
		mOrderId = getIntent().getStringExtra(KEY_ORDER_ID);
		
		initView();
		
	}
	
	void initView(){
		okBtn = (Button) findViewById(R.id.order_cancel_ok);
		reasonEt = (EditText) findViewById(R.id.order_cancel_reason);
		
		okBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String message = reasonEt.getText().toString();
				if(message ==null || message.length() == 0){
					UtilWidget.showToast(mContext, "请输入取消订单的原因");
					return;
				}
				
				new CommonRequestWrap(mContext, new ReqCancleOrder(mOrderId,message), new CommonRequestWrapDelegateAbstractImpl() {
					@Override
					public void requestServerSuccess(CommonResultVo resultVo) {
						UtilWidget.showAlertDialog(mContext, "你的订单已经取消", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								setResult(RESULT_OK);
								finish();
							}
						});
					}
				}).getRequest();
			}
		});
		
	}

	
}
