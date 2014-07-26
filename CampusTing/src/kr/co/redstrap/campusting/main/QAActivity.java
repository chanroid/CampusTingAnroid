package kr.co.redstrap.campusting.main;

import android.app.Activity;
import android.os.Bundle;

public class QAActivity extends Activity {

	private QALayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new QALayout(this);
		
		setContentView(layout.getView());
	}
	
}
