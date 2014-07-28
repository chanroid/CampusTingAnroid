package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.setting.PushAlarmLayout.Callback;
import android.app.Activity;
import android.os.Bundle;

public class PushAlarmActivity extends Activity implements Callback {
	
	private PushAlarmLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new PushAlarmLayout(this);
		layout.setCallback(this);
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onAlarmsetClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAlarmAM9Click() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAlarmPM7Click() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveTingClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConfirmClick() {
		// TODO Auto-generated method stub
		
	}

}
