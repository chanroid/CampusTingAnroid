package kr.co.redstrap.campusting.setting;

import android.app.Activity;
import android.os.Bundle;

public class ChangePWActivity extends Activity {

	private ChangePWLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new ChangePWLayout(this);
		
		setContentView(layout.getView());
	}
}
