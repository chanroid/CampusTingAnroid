package kr.co.redstrap.campusting.main;

import android.app.Activity;
import android.os.Bundle;

public class PurchaseActivity extends Activity implements PurchaseLayout.Callback {
	

	public PurchaseLayout layout;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new PurchaseLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
	}
}
