package kr.co.redstrap.campusting.setting;

import android.app.Activity;
import android.os.Bundle;

public class PolishActivity extends Activity implements PolishLayout.Callback {

	private PolishLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new PolishLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}
}
