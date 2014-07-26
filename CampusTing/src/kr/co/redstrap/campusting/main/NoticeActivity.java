package kr.co.redstrap.campusting.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class NoticeActivity extends Activity {

	private NoticeLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new NoticeLayout(this);
		
		setContentView(layout.getView());
	}

}
