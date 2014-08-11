package kr.co.redstrap.campusting.setting;

import android.app.Activity;
import android.os.Bundle;

public class UnregisterActivity extends Activity implements UnregisterLayout.Callback {

	private UnregisterLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new UnregisterLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onRestClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnregisterClick() {
		// TODO Auto-generated method stub
		
	}
	
	private void unregisterAction(int type) {
		
	}
}
