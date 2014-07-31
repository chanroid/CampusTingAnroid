package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.main.MatchingResultLayout.Callback;
import android.app.Activity;
import android.os.Bundle;

public class MatchingResultActivity extends Activity implements Callback {
	
	private MatchingResultLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new MatchingResultLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}
}
