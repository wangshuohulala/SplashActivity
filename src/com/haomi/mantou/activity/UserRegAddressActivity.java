package com.haomi.mantou.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.haomi.mantou.R;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrap;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrapDelegateAbstractImpl;
import com.haomi.mantou.base.loopj.requestdata.ReqPostCode;
import com.haomi.mantou.utils.UtilWidget;
import com.haomi.mantou.vo.PostcodeMapVo;
import com.haomi.mantou.vo.PostcodeMapVo.City;
import com.haomi.mantou.vo.PostcodeMapVoData;
import com.haomi.mantou.vo.base.CommonResultVo;


public class UserRegAddressActivity extends BaseActivity implements View.OnClickListener {

	private EditText addressDetailTv;
	private Button nextBtn;
	private Spinner mProvinceSpinner, mCitySpinner;
	private List<PostcodeMapVo> mPostcodeMapVo;
	
	private int indexProvince = 0, indexCity = 0, indexCountry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_reg_address);

		setTitle("输入家庭住址");
		
		initView();
		
		new CommonRequestWrap(this, new ReqPostCode(), new RequestDelegate()).getRequest();
		
	}
	
	void initView(){
		addressDetailTv = (EditText) findViewById(R.id.user_reg_address_detail);
		nextBtn = (Button) findViewById(R.id.user_reg_address_next);
		mProvinceSpinner = (Spinner) findViewById(R.id.user_reg_address_province);
		mCitySpinner = (Spinner) findViewById(R.id.user_reg_address_city);
		
		//mProvinceSpinner.setAdapter();
		
		nextBtn.setOnClickListener(this);
	}

	private class RequestDelegate extends
			CommonRequestWrapDelegateAbstractImpl {

		@Override
		public void requestServerSuccess(CommonResultVo resultVo) {

			Toast.makeText(mContext, resultVo.getMessage(), Toast.LENGTH_LONG).show();
			 mPostcodeMapVo = ((PostcodeMapVoData)resultVo).getData();
			 
			updateProvinceSpinner();
			mProvinceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					indexProvince = arg2;
					updateCitySpinner();
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
			mCitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					indexCity = arg2;
					//updateCountrySpinner();
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
		}

	}
	
	private void updateProvinceSpinner(){
		
		ArrayAdapter<PostcodeMapVo> provincesAdapter = new ArrayAdapter<PostcodeMapVo>(this,
				android.R.layout.simple_spinner_item,mPostcodeMapVo );
		provincesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mProvinceSpinner.setAdapter(provincesAdapter);
		
	}
	
	private void updateCitySpinner(){
		
		ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(this,
				android.R.layout.simple_spinner_item,mPostcodeMapVo.get(indexProvince).getCity() );
		cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCitySpinner.setAdapter(cityAdapter);
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			setResult(RESULT_OK);
			finish();
		}
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.user_reg_address_next:
			String address = addressDetailTv.getText().toString();
			String cityCode = mPostcodeMapVo.get(indexProvince).getCity().get(indexCity).getCitycode();
			if(cityCode == null || cityCode.length() == 0){
				UtilWidget.showToast(mContext, "请先选择城市");
				return;
			}
			if(address == null || address.length() == 0){
				UtilWidget.showToast(mContext, "请输入详情地址");
				return;
			}
			intent = new Intent(mContext, UserAddIdentificationActivity.class);
			intent.putExtra(UserAddIdentificationActivity.KEY_POSTCODE, cityCode);
			intent.putExtra(UserAddIdentificationActivity.KEY_ADDRESS, address);
			startActivityForResult(intent, 0);
			break;
		}
	}
	
}
