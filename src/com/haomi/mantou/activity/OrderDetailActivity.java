package com.haomi.mantou.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.haomi.mantou.R;
import com.haomi.mantou.base.imgloadconfig.ImgLoadUtil;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrap;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrapDelegateAbstractImpl;
import com.haomi.mantou.base.loopj.requestdata.ReqAcceptOrder;
import com.haomi.mantou.base.loopj.requestdata.ReqOrder;
import com.haomi.mantou.base.loopj.requestdata.ReqOrderDetail;
import com.haomi.mantou.utils.UtilWidget;
import com.haomi.mantou.vo.OrderDetailVo;
import com.haomi.mantou.vo.OrderDetailVoData;
import com.haomi.mantou.vo.base.CommonResultVo;

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

	private TextView titleTv;
	private TextView phoneTv, timeTv, addressTv;
	
	private TextView markEt;
	
	public static final String KEY_ORDER_ID = "KEY_ORDER_ID";
	private String mOrderId;
	
	private OrderDetailVo mOrderDetailVo;
	
	private Button mAcceptBtn;// 接单
	private Button mWashStartBtn;// 开始洗车
	private Button mWashFinishBtn;// 洗车完成
	private Button mNavigionBtn;// 导航
	private Button mCancleOrderBtn;// 取消订单
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);

		setTitle("订单详情");
		
		mOrderId = getIntent().getStringExtra(KEY_ORDER_ID);
		
		initView();
		
		new CommonRequestWrap(this, new ReqOrderDetail(mOrderId), new RequestDelegate()).getRequest();
	}
	
	void initView(){
		titleTv = (TextView) findViewById(R.id.order_detail_title);
		phoneTv = (TextView) findViewById(R.id.order_detail_phone);
		timeTv = (TextView) findViewById(R.id.order_detail_time);
		addressTv = (TextView) findViewById(R.id.order_detail_address);
		
		markEt = (TextView) findViewById(R.id.order_detail_mark);
		
		mAcceptBtn = (Button) findViewById(R.id.order_detail_receive_order);
		mWashStartBtn = (Button) findViewById(R.id.order_detail_start_wash);
		mWashFinishBtn = (Button) findViewById(R.id.order_detail_finish_wash);
		mNavigionBtn = (Button) findViewById(R.id.order_detail_navigation);
		mCancleOrderBtn = (Button) findViewById(R.id.order_detail_cancel_order);
		
		
		mAcceptBtn.setOnClickListener(this);
		mWashStartBtn.setOnClickListener(this);
		mWashFinishBtn.setOnClickListener(this);
		mNavigionBtn.setOnClickListener(this);
		mCancleOrderBtn.setOnClickListener(this);
		phoneTv.setOnClickListener(this);
		timeTv.setOnClickListener(this);
		addressTv.setOnClickListener(this);
	}
	
	void refreshUI(){
		if(mOrderDetailVo != null ){
			titleTv.setText(mOrderDetailVo.getService_title());
			phoneTv.setText(mOrderDetailVo.getMobile());
			timeTv.setText(mOrderDetailVo.getExpect_datetime());
			addressTv.setText(mOrderDetailVo.getLocation());
			((TextView) findViewById(R.id.order_detail_car_name)).setText(mOrderDetailVo.getCar_name());
			((TextView) findViewById(R.id.order_detail_car_color)).setText(mOrderDetailVo.getCar_color());
			((TextView) findViewById(R.id.order_detail_car_no)).setText(mOrderDetailVo.getCar_no());
			((TextView) findViewById(R.id.order_detail_mark)).setText(mOrderDetailVo.getMark());
			
			refreshOrderStatus(mOrderDetailVo.getStatus());
			//refreshOrderStatus(2);
			
			findViewById(R.id.order_detail_accept_layout).setVisibility(View.GONE);
			findViewById(R.id.order_detail_start_layout).setVisibility(View.GONE);
			findViewById(R.id.order_detail_finish_layout).setVisibility(View.GONE);
			if(mOrderDetailVo.getStatus() == ReqOrder.STATUS_CLOSE){
				findViewById(R.id.order_detail_accept_layout).setVisibility(View.VISIBLE);
				((TextView) findViewById(R.id.order_detail_accept_name)).setText("已接单");
				((TextView) findViewById(R.id.order_detail_accept_date)).setText(mOrderDetailVo.getAccept_time());
				
				findViewById(R.id.order_detail_start_layout).setVisibility(View.VISIBLE);
				((TextView) findViewById(R.id.order_detail_start_name)).setText("开始洗车");
				((TextView) findViewById(R.id.order_detail_start_date)).setText(mOrderDetailVo.getBegin_time());
				ImageView startImage = ((ImageView) findViewById(R.id.order_detail_start_image));
				ImgLoadUtil.displayImage(mOrderDetailVo.getBefore_image(), startImage);
				
				findViewById(R.id.order_detail_finish_layout).setVisibility(View.VISIBLE);
				((TextView) findViewById(R.id.order_detail_finish_name)).setText("洗车完成");
				((TextView) findViewById(R.id.order_detail_finish_date)).setText(mOrderDetailVo.getBegin_time());
				ImageView finishImage = ((ImageView) findViewById(R.id.order_detail_finish_image));
				ImgLoadUtil.displayImage(mOrderDetailVo.getBefore_image(), finishImage);
			}
			
			
		}
	}

	//订单详情
	private class RequestDelegate extends CommonRequestWrapDelegateAbstractImpl {

		@Override
		public void requestServerSuccess(CommonResultVo resultVo) {

			mOrderDetailVo = ((OrderDetailVoData) resultVo).getData();
			refreshUI();

		}

	}
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.order_detail_phone:
			try {
				String tel = "tel:"+phoneTv.getText().toString();
				intent = new Intent(Intent.ACTION_DIAL,Uri.parse(tel)); 
			    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			    startActivity(intent);
			} catch (Exception e) {
				UtilWidget.showToast(this, "不支持电话功能");
			}
			
			break;
		case R.id.order_detail_address:
			try {
				intent = new Intent(Intent.ACTION_VIEW); 
				Uri addressUrl = Uri.parse("geo:39.922840,116.3543240,北京市西城区阜外大街2号万通大厦"); 
				intent.setData(addressUrl); 
				//intent.setClassName("com.mapbar.android.mapbarmap", "com.mapbar.android.mapbarmap.FilterServiceActivity"); 
				startActivity(intent); 
			} catch (Exception e) {
				UtilWidget.showToast(this, "导航加载失败");
			}
			
			break;
		case R.id.order_detail_receive_order:
			
			new CommonRequestWrap(this, new ReqAcceptOrder(mOrderId), new CommonRequestWrapDelegateAbstractImpl() {
				@Override
				public void requestServerSuccess(CommonResultVo resultVo) {
					refreshOrderStatus(ReqOrder.STATUS_ACCEPT);
				}
			}).getRequest();
			
			break;
		case R.id.order_detail_start_wash:
			intent = new Intent(mContext, WashAddPhotoStartActivity.class);
			intent.putExtra(OrderCancelActivity.KEY_ORDER_ID, mOrderId);
			startActivity(intent);
			break;
		case R.id.order_detail_finish_wash:
			intent = new Intent(mContext, WashAddPhotoFinishActivity.class);
			intent.putExtra(OrderCancelActivity.KEY_ORDER_ID, mOrderId);
			startActivity(intent);
			break;
		case R.id.order_detail_navigation:
			try {
				intent = new Intent(Intent.ACTION_VIEW); 
				Uri addressUrl = Uri.parse("geo:39.922840,116.3543240,北京市西城区阜外大街2号万通大厦"); 
				intent.setData(addressUrl); 
				//intent.setClassName("com.mapbar.android.mapbarmap", "com.mapbar.android.mapbarmap.FilterServiceActivity"); 
				startActivity(intent); 
			} catch (Exception e) {
				UtilWidget.showToast(this, "导航加载失败");
			}
			
			break;
		case R.id.order_detail_cancel_order:
			intent = new Intent(mContext, OrderCancelActivity.class);
			intent.putExtra(OrderCancelActivity.KEY_ORDER_ID, mOrderId);
			startActivityForResult(intent, 1);
			
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK && requestCode == 1){//取消订单成功
			finish();
		}
		
	}
	void refreshOrderStatus(int orderStatus){
		
		mAcceptBtn.setVisibility(View.GONE);
		mWashStartBtn.setVisibility(View.GONE);
		mNavigionBtn.setVisibility(View.GONE);
		mCancleOrderBtn.setVisibility(View.GONE);
		
		
		switch (orderStatus) {
		case ReqOrder.STATUS_OPEN:
			mAcceptBtn.setVisibility(View.VISIBLE);
			break;
		case ReqOrder.STATUS_ACCEPT:
			mNavigionBtn.setVisibility(View.VISIBLE);
			mCancleOrderBtn.setVisibility(View.VISIBLE);
			mWashStartBtn.setVisibility(View.VISIBLE);
			break;
		case ReqOrder.STATUS_BEGIN:
			mWashFinishBtn.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

}
