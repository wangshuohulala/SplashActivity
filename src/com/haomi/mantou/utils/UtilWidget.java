package com.haomi.mantou.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class UtilWidget {

	private static AlertDialog mAlertDlg;
	private static ProgressDialog mProgressDlg;
	
	public static void showToast(Context context, String message) {
		showToast(context, message, Toast.LENGTH_SHORT);
	}
	public static void showToast(Context context, String message, int duration) {
		Toast.makeText(context, message, duration).show();
	}

	

	public static void showToast(Context context, int resId) {
		///Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
		showToast(context,context.getString(resId),Toast.LENGTH_SHORT);
	}
	public static void showToast(Context context, int resId, int duration) {
		///Toast.makeText(context, resId, duration).show();
		showToast(context,context.getString(resId),duration);
	}
	
	
	
	/**
	 * 得到自定义的progressDialog
	 * @param context
	 * @param msg
	 * @return
	 */
	/*public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.custom_progress_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_anim);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 设置加载信息

		Dialog loadingDialog = new Dialog(context, R.style.loding_dialog);// 创建自定义样式dialog
		
		//loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout);// 设置布局
		return loadingDialog;

	}
	//private static ProgressDialog mProgressDlg;
	public static void showProgressDialog(Context context,int res,boolean cancelable){
		showProgressDialog(context, context.getString(res),cancelable);
	}
	public static void showProgressDialog(Context context,String message,boolean cancelable){
		 if(mProgressDlg !=null){
				mProgressDlg.cancel(); //防止执行两次showProgressDialog()
				mProgressDlg = null;
			}
			mProgressDlg = createLoadingDialog(context,message); //每次用时确保是新的
			mProgressDlg.setCancelable(cancelable);
			mProgressDlg.show();
		 ;
	}
	*/
	
	 
	public static Dialog showProgressDialog(Context context,int res,boolean cancelable){
		return showProgressDialog(context, context.getString(res),cancelable);
	}
	public static Dialog showProgressDialog(Context context,String message,boolean cancelable){
		if(context == null || ( context instanceof Activity && ((Activity) context).isFinishing())){
			return null;
		}
			
		if(mProgressDlg !=null){
			mProgressDlg.cancel(); //防止执行两次showProgressDialog()
			mProgressDlg = null;
		}
		mProgressDlg = new ProgressDialog(context); //每次用时确保是新的
		
		mProgressDlg.setMessage(message);
		mProgressDlg.setCancelable(cancelable);
		mProgressDlg.show();
		return mProgressDlg;
		
		
	} 
	
	public static void updateProgressDlg(String message){
		if(mProgressDlg != null){
			mProgressDlg.setMessage(message);
		}
	}
	
	
	public static void cancelProgressDialog(){
		if(mProgressDlg != null){
			mProgressDlg.cancel();
			mProgressDlg = null;
		}
	}
	public static Dialog showAlertDialog(Context context, int messageRes) {
		return showAlertDialog(context, context.getString(messageRes));
	}
	public static Dialog showAlertDialog(Context context, String message) {
		
		mAlertDlg = new AlertDialog.Builder(context)
				.setTitle("提示")
				
				.setMessage(message)
				.setPositiveButton("确定", new Dialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mAlertDlg.cancel();
					}
				})
				.create();
		mAlertDlg.setIcon(android.R.drawable.ic_dialog_alert);
		mAlertDlg.show();

		return mAlertDlg;
	}
	
	public static Dialog showAlertDialog(Context context, String message,Dialog.OnClickListener listener) {
		
		mAlertDlg = new AlertDialog.Builder(context)
		.setTitle("提示")
		
		.setMessage(message)
		.setPositiveButton("确定", listener)
		.create();
		mAlertDlg.setIcon(android.R.drawable.ic_dialog_alert);
		mAlertDlg.show();

		return mAlertDlg;
	}
	
	public static void cancelAlertDialog(){
		if(mAlertDlg!=null){
			mAlertDlg.cancel();
			mAlertDlg = null;
		}
	}
	
	
}
