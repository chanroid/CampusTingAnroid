package kr.co.redstrap.campusting.setting;

import android.app.Activity;
import android.os.Bundle;

public class ChangePWActivity extends Activity implements ChangePWLayout.Callback {

	private ChangePWLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new ChangePWLayout(this);
		
		setContentView(layout.getView());
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onChangePWClick() {
		// TODO Auto-generated method stub
		
	}
}
