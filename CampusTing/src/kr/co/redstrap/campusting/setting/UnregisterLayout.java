package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class UnregisterLayout extends AbsCTLayout {
	
	public interface Callback {
		public void onBackClick();
		public void onRestClick();
		public void onUnregisterClick();
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	private ImageButton backBtn;
	private Button restBtn;
	private Button unregisterBtn;
	
	public UnregisterLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_unregister;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		Listener l = new Listener();
		
		backBtn = (ImageButton) findViewById(R.id.unregisterBackBtn);
		backBtn.setOnClickListener(l);
		restBtn = (Button) findViewById(R.id.unregisterRestBtn);
		restBtn.setOnClickListener(l);
		unregisterBtn = (Button) findViewById(R.id.unregisterBtn);
		unregisterBtn.setOnClickListener(l);
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.unregisterBackBtn:
				callback.onBackClick();
				break;
			case R.id.unregisterBtn:
				callback.onUnregisterClick();
				break;
			case R.id.unregisterRestBtn:
				callback.onRestClick();
				break;
			}
		}
		
	}

}
