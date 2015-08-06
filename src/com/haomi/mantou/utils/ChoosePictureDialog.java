package com.haomi.mantou.utils;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class ChoosePictureDialog {
	public static final String TAG = "ChoosePictureDialog";
	public static final int RequestCodeChosePicture = 21;
	public static final int RequestCodeChoseCamera = 22;
	public static final int RequestCodeEditImage = 23;
	private Context mContext;
	private String mSaveCameraFile;//用来保存拍照时的图片路径
	
	private boolean FLAG_EDIT_IMAGE = false;//开关，用来控制是否处理图片
	
	public ChoosePictureDialog(Context context) {
		mContext = context;
	}

	String[] items = {"拍照","选择图片"};
	public void showDialog(){
		new AlertDialog.Builder(mContext)
        .setTitle("请选择照片")
        .setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	switch (which) {
				case 0:
					startCamera(RequestCodeChoseCamera);
					break;
				case 1:
					startPicture(RequestCodeChosePicture);
					break;
				default:
					break;
				}
            }
        })
        .create().show();
	}
	
	/**
	 * 调用相机，和图库后，需要调用
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK){
			if(requestCode == RequestCodeChosePicture && data != null && data.getData() != null){
				Uri selectedImage = data.getData();
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	            Cursor cursor = mContext.getContentResolver().query(selectedImage,
	                    filePathColumn, null, null, null);
	            cursor.moveToFirst();
	 
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            cursor.close();
	            
	            Log.i(TAG, "chose picture path="+picturePath);
	          
//	            if(FLAG_EDIT_IMAGE){
//		            Intent intent = new Intent(mContext, EditImageActivity.class);
//					intent.putExtra(EditImageActivity.KEY_IMAGE_PATH, picturePath);
//					((Activity)mContext).startActivityForResult(intent,RequestCodeEditImage);
//	            }else{
	            	if(mOnChoosePictureListener != null){
						mOnChoosePictureListener.onChoosePictureResult(PictureCompress.getCompressImagePath(picturePath));
					//}
	            }
			}else if(requestCode == RequestCodeChoseCamera ){
				String picturePath = mSaveCameraFile;
				Log.i(TAG, "chose camera path="+picturePath);
//				if(FLAG_EDIT_IMAGE){
//					Intent intent = new Intent(mContext, EditImageActivity.class);
//					intent.putExtra(EditImageActivity.KEY_IMAGE_PATH, picturePath);
//					((Activity)mContext).startActivityForResult(intent,RequestCodeEditImage);
//				}else{
					if(mOnChoosePictureListener != null){
						mOnChoosePictureListener.onChoosePictureResult(PictureCompress.getCompressImagePath(picturePath));
					//}
				}
			}else if(requestCode == RequestCodeEditImage){
				//String picturePath = data.getStringExtra(EditImageActivity.KEY_RESULT_IMIAGE_PATH);
				//if(mOnChoosePictureListener != null){
				//	mOnChoosePictureListener.onChoosePictureResult(picturePath);
				//}
				
			}
			
		}
	}
	
	/**
	 * 启动相机
	 * @param requestCode
	 */
	private void startCamera(int requestCode) {
		try {
			Intent capIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			mSaveCameraFile =  GouminFileUtil.getEditImageCacheFile();
			capIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mSaveCameraFile)));
			((Activity)mContext).startActivityForResult(capIntent, requestCode);
		} catch (Exception e) {
			e.printStackTrace();
			UtilWidget.showToast(mContext, "无法启动相机");
		}
	}
	/**
	 * 启动图库
	 * @param requestCode
	 */
	private void startPicture(int requestCode) {
		/*Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		((Activity)mContext).startActivityForResult(intent, requestCode);
		*/
		try {
			Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			((Activity) mContext).startActivityForResult(i, requestCode);
		} catch (Exception e) {
			e.printStackTrace();
			UtilWidget.showToast(mContext, "无法启动图库");
		}
	}
	
	
	public interface OnChoosePictureListener{
		public void onChoosePictureResult(String imagePath);
	}
	
	private OnChoosePictureListener mOnChoosePictureListener;
	
	public void setOnChoosePictureListener(OnChoosePictureListener listener){
		mOnChoosePictureListener = listener;
	}
}
