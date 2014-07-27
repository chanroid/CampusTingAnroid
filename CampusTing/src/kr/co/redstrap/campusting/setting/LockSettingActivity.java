package kr.co.redstrap.campusting.setting;

import android.app.Activity;
import android.os.Bundle;

public class LockSettingActivity extends Activity {

	private LockSettingLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new LockSettingLayout(this);
		
		setContentView(layout.getView());
	}
}
