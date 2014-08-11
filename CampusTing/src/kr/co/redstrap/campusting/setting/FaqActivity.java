package kr.co.redstrap.campusting.setting;

import android.app.Activity;
import android.os.Bundle;

public class FaqActivity extends Activity implements FaqLayout.Callback {
	
	private FaqLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new FaqLayout(this);
		layout.setCallback(this);
		setContentView(layout.getView());
	}
	
	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

}
