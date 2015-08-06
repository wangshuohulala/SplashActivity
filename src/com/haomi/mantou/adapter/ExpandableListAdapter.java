package com.haomi.mantou.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haomi.mantou.R;
import com.haomi.mantou.vo.IncomeListVo;
import com.haomi.mantou.vo.IncomeListVo.IncomeOrder;

public class ExpandableListAdapter extends BaseExpandableListAdapter{

	Context mContext;
	
	
	private List<IncomeListVo> mDataList ;
	public ExpandableListAdapter(Context context, List<IncomeListVo> list) {
		mContext = context;
		mDataList = list;
		
	}
	@Override
	public int getGroupCount() {
		return mDataList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mDataList.get(groupPosition).getOrder().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mDataList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return mDataList.get(groupPosition).getOrder().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		LinearLayout newsView;
        if(convertView == null) {
            newsView = new LinearLayout(mContext);
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(inflater);
            vi.inflate(R.layout.income_group_item, newsView, true);
        } else {
            newsView = (LinearLayout)convertView;
        }
        
        TextView dateTv = (TextView) newsView.findViewById(R.id.income_group_item_date);
        dateTv.setText(mDataList.get(groupPosition).getDate());
        TextView totalMoneyTv = (TextView) newsView.findViewById(R.id.income_group_item_total_money);
        totalMoneyTv.setText(mDataList.get(groupPosition).getTotalmoney());
        
        ImageView arrow = (ImageView) newsView.findViewById(R.id.income_group_item_arrow);
        if(isExpanded){
        	arrow.setImageResource(R.drawable.ic_circle_arrow_down);
        }else{
        	arrow.setImageResource(R.drawable.ic_circle_arrow_right);        	
        }
        return newsView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		LinearLayout newsView;
        if(convertView == null) {
            newsView = new LinearLayout(mContext);
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(inflater);
            vi.inflate(R.layout.income_child_item, newsView, true);
        } else {
            newsView = (LinearLayout)convertView;
        }
        
        IncomeOrder  order = mDataList.get(groupPosition).getOrder().get(childPosition);
        TextView dateTv = (TextView) newsView.findViewById(R.id.income_child_item_date);
        dateTv.setText(order.getOrder_time());
        TextView carTv = (TextView) newsView.findViewById(R.id.income_child_item_car);
        carTv.setText(order.getCarInfo());
        TextView priceTv = (TextView) newsView.findViewById(R.id.income_child_item_price);
        priceTv.setText(order.getPrice());
        
        
        return newsView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	
	
}