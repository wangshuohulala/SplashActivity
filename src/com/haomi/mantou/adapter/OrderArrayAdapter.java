package com.haomi.mantou.adapter;


import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haomi.mantou.R;
import com.haomi.mantou.activity.OrderDetailActivity;
import com.haomi.mantou.base.loopj.requestdata.ReqOrder;
import com.haomi.mantou.vo.OrderVo;

public class OrderArrayAdapter extends ArrayAdapter {
    Context mContext;
    public static final int NWES_ITEM = 0;
    public static final int PICTURE_ITEM = 1;
    public static final int LIST_ITEM = 2;
    public static final int XIANGCE_ITEM = 3;
    public static int RESOURCEID;

    private ArrayList<OrderVo> mDataList;

    public OrderArrayAdapter(Context context, int resourceId, ArrayList<OrderVo> objects) {
        super(context, resourceId, objects);
        mContext = context;
        RESOURCEID = resourceId;
        mDataList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout newsView;
        if(convertView == null) {
            newsView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(RESOURCEID, newsView, true);
        } else {
            newsView = (LinearLayout)convertView;
        }
        
        OrderVo order = mDataList.get(position);

        ((TextView) newsView.findViewById(R.id.order_item_time)).setText(order.getExpect_datetime());
        ((TextView) newsView.findViewById(R.id.order_item_location)).setText(order.getLocation());
        ((TextView) newsView.findViewById(R.id.order_item_price)).setText(order.getOrder_price() +"----状态："+order.getStatus());
            
        Button goOrder = (Button) newsView.findViewById(R.id.order_item_go_order);
        
        if(order.getStatus() == ReqOrder.STATUS_OPEN){
        	goOrder.setText("去接单");
        }else{
        	goOrder.setText("看订单");
        	
        }
        goOrder.setTag(order.getOrder_no());
        goOrder.setOnClickListener(listener);
        
        return newsView;
    }

    View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String orderId = (String) v.getTag();
			Intent intent = new Intent(mContext, OrderDetailActivity.class);
			//Intent intent = new Intent(mContext, WashAddPhotoActivity.class);
			intent.putExtra(OrderDetailActivity.KEY_ORDER_ID, orderId);
			mContext.startActivity(intent);
		}
	};

}