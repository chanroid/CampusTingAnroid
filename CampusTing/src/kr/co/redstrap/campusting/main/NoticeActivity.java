package kr.co.redstrap.campusting.main;

import android.app.Activity;
import android.os.Bundle;

public class NoticeActivity extends Activity implements NoticeLayout.Callback {

	private NoticeLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new NoticeLayout(this);
		
		setContentView(layout.getView());
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

}
