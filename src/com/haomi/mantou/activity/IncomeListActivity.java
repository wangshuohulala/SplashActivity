package com.haomi.mantou.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.haomi.mantou.R;
import com.haomi.mantou.adapter.ExpandableListAdapter;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrap;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrapDelegateAbstractImpl;
import com.haomi.mantou.base.loopj.requestdata.ReqIncomeList;
import com.haomi.mantou.vo.IncomeListVo;
import com.haomi.mantou.vo.IncomeListVoData;
import com.haomi.mantou.vo.base.CommonResultVo;

/**
 * 收入明细
 * @author zhf
 *
 */
public class IncomeListActivity extends BaseActivity implements View.OnClickListener{

	private ExpandableListView mListView;
	private ExpandableListAdapter mAdatper;
	
	private ReqIncomeList mReqest = new ReqIncomeList();
	private List<IncomeListVo> mDataList = new ArrayList<IncomeListVo>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_list);
        setTitle("收入明细");
        mListView = (ExpandableListView) findViewById(R.id.expandable_listview);
        mListView.setGroupIndicator(null);
        mAdatper = new ExpandableListAdapter(this, mDataList);
        mListView.setAdapter(mAdatper);
        
      //设置item点击的监听器
        mListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {

				Toast.makeText(mContext, "你点击了" + childPosition,
						Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        reqOrder();
    }

    
    private void reqOrder(){
    	
    	new CommonRequestWrap(this, mReqest, new RequestDelegate()).getRequest();
    	
    }
    
    private class RequestDelegate extends CommonRequestWrapDelegateAbstractImpl {

		@Override
		public void requestServerSuccess(CommonResultVo resultVo) {

			List<IncomeListVo> list = ((IncomeListVoData) resultVo).getData();
			
			if(mReqest.page == 1){
				mDataList.clear();
				mDataList.addAll(list);
				
			}else{
				for (int i = 0; i < list.size(); i++) {
					mDataList.add(list.get(i));
				}
			}
			mAdatper.notifyDataSetChanged();
			mListView.expandGroup(0, true);
		}

	}
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		/*case R.id.order_item_go_order:
			intent = new Intent(mContext, OrderDetailActivity.class);
			startActivity(intent);
			break;*/
		default:
			break;
		}
	}
}