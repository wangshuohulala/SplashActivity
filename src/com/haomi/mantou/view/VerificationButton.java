package com.haomi.mantou.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.Button;

import com.haomi.mantou.R;

public class VerificationButton extends Button{
	
	public VerificationButton(Context context) {
		this(context, null, 0);
	}
	public VerificationButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VerificationButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		setText(R.string.get_vertify_code);
	}
	
	public void getVerificationCode(int countTime){
		setClickable(false);
		
		new MyCountDownTimer(countTime*1000, 1000).start();
		
	}
	
	class MyCountDownTimer extends CountDownTimer{

		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		

		@Override
		public void onTick(long millisUntilFinished) {
			setText(millisUntilFinished/1000 +"");
			
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			setText(R.string.get_vertify_code);
			setClickable(true);
			
		}
		
	}

}
