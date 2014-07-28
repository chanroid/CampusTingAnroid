package kr.co.redstrap.campusting.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LockSettingActivity extends Activity implements LockSettingLayout.Callback {

	private LockSettingLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new LockSettingLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onLockToggleClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLockPasswordChangeClick() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, LockPasswordChangeActivity.class));
	}
}
