package kr.co.redstrap.campusting.setting;

import android.app.Activity;
import android.os.Bundle;

public class LockPasswordChangeActivity extends Activity {

	private LockPasswordChangeLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new LockPasswordChangeLayout(this);
		
		setContentView(layout.getView());
	}
}
