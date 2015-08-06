package com.haomi.mantou.activity;

import java.util.HashMap;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.haomi.mantou.R;
import com.haomi.mantou.MantouApplication;
import com.haomi.mantou.base.imgloadconfig.ImgLoadUtil;
import com.haomi.mantou.base.loopj.requestclient.UploadImagesService;
import com.haomi.mantou.base.loopj.requestdata.ReqOrderWashFinish;
import com.haomi.mantou.utils.ChoosePictureDialog;
import com.haomi.mantou.utils.ChoosePictureDialog.OnChoosePictureListener;
import com.haomi.mantou.utils.UtilWidget;
import com.haomi.mantou.vo.base.CommonResultVo;

public class WashAddPhotoFinishActivity extends BaseActivity implements View.OnClickListener {

	private ImageView mImageView;
	private Button submitBtn;
	private TextView explainTv;
	private ChoosePictureDialog mChoosePictureDialog;
	
	public static final String KEY_ORDER_ID = "KEY_ORDER_ID";
	private String mOrderId;
	private String mImagePath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wash_add_photo);

		setTitle("洗车完成");
		
		mOrderId = getIntent().getStringExtra(KEY_ORDER_ID);
		
		initView();
		
	}
	
	void initView(){
		mImageView = (ImageView) findViewById(R.id.wash_add_photo_img);
		submitBtn = (Button) findViewById(R.id.wash_add_photo_ok);
		explainTv = (TextView) findViewById(R.id.wash_add_photo_explain);
		explainTv.setText("请拍下洗车后的车辆照片");
		
		mImageView.setOnClickListener(this);
		submitBtn.setOnClickListener(this);
		
		mChoosePictureDialog = new ChoosePictureDialog(this);
		mChoosePictureDialog.setOnChoosePictureListener(new OnChoosePictureListener() {
			@Override
			public void onChoosePictureResult(String imagePath) {
				ImgLoadUtil.displayImage("file:///"+imagePath, mImageView);
				mImagePath = imagePath;
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mChoosePictureDialog.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.wash_add_photo_img:
			mChoosePictureDialog.showDialog();
			break;
		case R.id.wash_add_photo_ok:
			//finish();
			new MyTask().execute();
			
			break;
		
		default:
			break;
		}
	}
	
	class MyTask extends AsyncTask<String, Integer, CommonResultVo>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			UtilWidget.showProgressDialog(mContext, R.string.loading, false);
		}
		@Override
		protected CommonResultVo doInBackground(String... params) {
			UploadImagesService upload = new UploadImagesService();
			
			HashMap<String, String> image = new HashMap<String, String>();
			image.put("image", mImagePath);
			
			CommonResultVo ret = upload.uploadImage(new ReqOrderWashFinish(mOrderId), image);
			
			return ret;
		}
		
		@Override
		protected void onPostExecute(CommonResultVo result) {
			UtilWidget.cancelProgressDialog();			
			if(result.getCode().equals("100")){
				UtilWidget.showAlertDialog(mContext, "你的派单已经完成",new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						MantouApplication.getInstance().goMainActivityfinishOther();
					}
				});
				
			}else{
				UtilWidget.showToast(mContext, result.getMessage());
			}
			
		}
		
	}

}
