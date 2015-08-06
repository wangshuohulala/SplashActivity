package com.haomi.mantou.base.loopj.requestclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.haomi.mantou.base.loopj.requestclient.CommonRequestWrap.CommonRequestWrapDelegate;
import com.haomi.mantou.vo.base.CommonResultVo;

public abstract class CommonRequestWrapDelegateAbstractImpl implements CommonRequestWrapDelegate {
	
	@Override
	public void requestServerStart(ProgressDialog progressDialog) {
		RequestProgressDialogWrap.showProgressDialog(progressDialog);
	}

	@Override
	public void requestServerNetWorkError() {
	}
	
	public abstract void requestServerSuccess(CommonResultVo resultVo);

	@Override
	public void requestServerFailure(Context cxt, String resultCode, String errorMsg){
		Toast.makeText(cxt, errorMsg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void requestServerResponseError(Context cxt) {
		Toast.makeText(cxt, "请求失败", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void requestServerEnd(ProgressDialog progressDialog) {
		RequestProgressDialogWrap.dismissProgressDialog(progressDialog);
	}

}
