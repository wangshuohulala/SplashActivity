package com.haomi.mantou.activity;

import java.util.HashMap;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.haomi.mantou.R;
import com.haomi.mantou.base.imgloadconfig.ImgLoadUtil;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrap;
import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrapDelegateAbstractImpl;
import com.haomi.mantou.base.loopj.requestclient.UploadImagesService;
import com.haomi.mantou.base.loopj.requestdata.ReqBindAddressId;
import com.haomi.mantou.base.loopj.requestdata.ReqOrderWashBegin;
import com.haomi.mantou.utils.ChoosePictureDialog;
import com.haomi.mantou.utils.ChoosePictureDialog.OnChoosePictureListener;
import com.haomi.mantou.utils.UtilWidget;
import com.haomi.mantou.vo.base.CommonResultVo;

public class UserAddIdentificationActivity extends BaseActivity implements View.OnClickListener {

	private ImageView mImageView1,mImageView2;
	private Button submitBtn;
	private ChoosePictureDialog mChoosePictureDialog;
	
	private boolean isImage1;
	
	public static final String KEY_POSTCODE = "KEY_POSTCODE";
	public static final String KEY_ADDRESS = "KEY_ADDRESS";
	
	private String mPostcode, mAddress;
	
	private String mImagePath1, mImagePath2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activit_add_identity_card);

		setTitle("身份认证");
		
		mPostcode = getIntent().getStringExtra(KEY_POSTCODE);
		mAddress = getIntent().getStringExtra(KEY_ADDRESS);
		
		initView();
		
	}
	
	void initView(){
		mImageView1 = (ImageView) findViewById(R.id.add_photo_img1);
		mImageView2 = (ImageView) findViewById(R.id.add_photo_img2);
		submitBtn = (Button) findViewById(R.id.join);
		
		mImageView1.setOnClickListener(this);
		mImageView2.setOnClickListener(this);
		submitBtn.setOnClickListener(this);
		
		mChoosePictureDialog = new ChoosePictureDialog(this);
		mChoosePictureDialog.setOnChoosePictureListener(new OnChoosePictureListener() {
			
			@Override
			public void onChoosePictureResult(String imagePath) {
				if(isImage1){
					mImagePath1 = imagePath;
					ImgLoadUtil.displayImage("file:///"+imagePath, mImageView1);
				} else{
					mImagePath2 = imagePath;
					ImgLoadUtil.displayImage("file:///"+imagePath, mImageView2);
				}
				
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mChoosePictureDialog.onActivityResult(requestCode, resultCode, data);
	}

	private class RequestDelegate extends CommonRequestWrapDelegateAbstractImpl {

		@Override
		public void requestServerSuccess(CommonResultVo resultVo) {

			UtilWidget.showAlertDialog(mContext, "注册成功，等待审批",new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					setResult(RESULT_OK);
					finish();
					
				}
			});
		}

	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.add_photo_img1:
			isImage1 = true;
			mChoosePictureDialog.showDialog();
			break;
		case R.id.add_photo_img2:
			isImage1 = false;
			mChoosePictureDialog.showDialog();
			break;
		case R.id.join:
			
			
			if(mImagePath1 == null){
				UtilWidget.showToast(mContext, "请选择上传身份证正面照");
			} else if(mImagePath2 == null){
				UtilWidget.showToast(mContext, "请选择上传身份证反面照");
			}
			
			ReqBindAddressId req = new ReqBindAddressId(mPostcode, mAddress);
			HashMap<String, String> image = new HashMap<String, String>();
			image.put("images[0]", mImagePath1);
			image.put("images[1]", mImagePath2);
			
			///new CommonRequestWrap(this, req, new RequestDelegate()).postImage(image);
			
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
			image.put("images[0]", mImagePath1);
			image.put("images[1]", mImagePath2);
			
			CommonResultVo ret = upload.uploadImage(new ReqBindAddressId(mPostcode, mAddress), image);
			
			return ret;
		}
		
		@Override
		protected void onPostExecute(CommonResultVo result) {
			UtilWidget.cancelProgressDialog();
			
			if(result.getCode().equals("100")){
				UtilWidget.showAlertDialog(mContext, "注册成功，等待审批",new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						setResult(RESULT_OK);
						finish();
						
					}
				});
			}else{
				UtilWidget.showToast(mContext, result.getMessage());
			}
		}
		
	}


}
