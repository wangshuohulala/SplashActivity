package com.haomi.mantou.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.haomi.mantou.R;
import com.haomi.mantou.adapter.OrderArrayAdapter;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrap;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrapDelegateAbstractImpl;
import com.haomi.mantou.base.loopj.requestdata.ReqCommon;
import com.haomi.mantou.base.loopj.requestdata.ReqOrder;
import com.haomi.mantou.base.loopj.requestdata.ReqWorkStatus;
import com.haomi.mantou.global.InterfaceUrlDefine;
import com.haomi.mantou.vo.OrderVo;
import com.haomi.mantou.vo.OrderVoData;
import com.haomi.mantou.vo.WorkStatusVoData;
import com.haomi.mantou.vo.base.CommonResultVo;

public class OrderActivity extends BaseActivity implements View.OnClickListener{

	
	public static final String KEY_ORDER_TAG = "KEY_ORDER_TAG";
	public static final int ORDER_TAG_WAIT_ORDER = 1;// 待接单
	public static final int ORDER_TAG_WAIT_WASH = 2;// 待清洗
	public static final int ORDER_TAG_FINISHED = 3;// 已完成
	
	private ListView mListView;
	private OrderArrayAdapter mAdatper;
	private Button mWorkStatusBtn;
	private RadioGroup mRadioGroup; 
	
	private ArrayList<OrderVo> mList = new ArrayList<OrderVo>();
	private ReqOrder mReqOrder = new ReqOrder();
	
	
	private String[] items = {"上班","下班", "取消"};
	
	private int mOrderTag ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        
        mOrderTag = getIntent().getIntExtra(KEY_ORDER_TAG, ORDER_TAG_WAIT_ORDER);
        mReqOrder.ordertag = mOrderTag;
        
        mListView = (ListView) findViewById(R.id.order_listview);
        mAdatper = new OrderArrayAdapter(this, R.layout.order_item, mList);
        mListView.setAdapter(mAdatper);
        
        mRadioGroup = (RadioGroup) findViewById(R.id.order_radiogroup);
        mWorkStatusBtn = (Button) findViewById(R.id.order_work_status);
        mWorkStatusBtn.setOnClickListener(this);
        
        mRadioGroup.clearCheck();
        switch (mOrderTag) {
		case ORDER_TAG_WAIT_ORDER:
			mRadioGroup.check(R.id.order_wait_order);
			break;
		case ORDER_TAG_WAIT_WASH:
			mRadioGroup.check(R.id.order_wait_wash);
			break;
		case ORDER_TAG_FINISHED:
			mRadioGroup.check(R.id.order_finish);
			break;
		default:
			break;
		}
        
        //mReqOrder.status = 0;// all
       
        reqOrder();
        
        //解决服务器2秒内请求太频繁
        mWorkStatusBtn.postDelayed(new Runnable() {
			@Override
			public void run() {
				new CommonRequestWrap(mContext, new ReqWorkStatus(), new RequestWordStatusDelegate()).getRequest();
			}
		}, 3000);
        
        
        mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.order_wait_order:
					mReqOrder.page = 1;
					mReqOrder.ordertag = ORDER_TAG_WAIT_ORDER;
			        reqOrder();
					break;
				case R.id.order_wait_wash:
					mReqOrder.page = 1;
					mReqOrder.ordertag = ORDER_TAG_WAIT_WASH;
			        reqOrder();
					break;
				case R.id.order_finish:
					mReqOrder.page = 1;
					mReqOrder.ordertag = ORDER_TAG_FINISHED;
			        reqOrder();
					break;
				default:
					break;
				}
				
			}
		});
    }
    
    private void reqOrder(){
    	
    	new CommonRequestWrap(this, mReqOrder, new RequestDelegate()).getRequest();
    	
    }
    
    private class RequestDelegate extends CommonRequestWrapDelegateAbstractImpl {

		@Override
		public void requestServerSuccess(CommonResultVo resultVo) {

			List<OrderVo> list = ((OrderVoData) resultVo).getData();
			
			if(mReqOrder.page == 1){
				mList.clear();
				mList.addAll(list);
				
			}else{
				for (int i = 0; i < list.size(); i++) {
					mList.add(list.get(i));
				}
			}
			mAdatper.notifyDataSetChanged();
			
		}

	}
    
    private class RequestWordStatusDelegate extends CommonRequestWrapDelegateAbstractImpl {

		@Override
		public void requestServerSuccess(CommonResultVo resultVo) {

			WorkStatusVoData result = (WorkStatusVoData) resultVo;
			if(result.getData().isStatus()){
				mWorkStatusBtn.setText(items[0]);
			}else{
				mWorkStatusBtn.setText(items[1]);
			}
			
		}
    }

    private class RequestPauchInDelegate extends CommonRequestWrapDelegateAbstractImpl {

		@Override
		public void requestServerSuccess(CommonResultVo resultVo) {
			mWorkStatusBtn.setText(items[0]);
		}
    }
    private class RequestPauchOutDelegate extends CommonRequestWrapDelegateAbstractImpl {

		@Override
		public void requestServerSuccess(CommonResultVo resultVo) {
			mWorkStatusBtn.setText(items[1]);
		}
    }
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.order_work_status:
			new AlertDialog.Builder(mContext).setItems(items, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which == 0){
						new CommonRequestWrap(mContext, new ReqCommon(InterfaceUrlDefine.URL_PUNCH_IN), new RequestPauchInDelegate()).getRequest();
						
					}else if(which == 1){
						new CommonRequestWrap(mContext, new ReqCommon(InterfaceUrlDefine.URL_PUNCH_OUT), new RequestPauchOutDelegate()).getRequest();
					}
					
				}
			}).create()
			.show();
			break;
		default:
			break;
		}
	}
}