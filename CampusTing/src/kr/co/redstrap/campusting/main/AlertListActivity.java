package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.main.AlertListLayout.Callback;
import android.app.Activity;
import android.os.Bundle;

public class AlertListActivity extends Activity implements Callback {

	private AlertListLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new AlertListLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}
	
}
