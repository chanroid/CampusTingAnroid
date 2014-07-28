package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LockSettingLayout extends AbsCTLayout {
	
	public interface Callback {
		public void onBackClick();
		public void onLockToggleClick();
		public void onLockPasswordChangeClick();
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	private ImageButton backBtn;
	private RelativeLayout lockToggleBtn;
	private TextView lockToggleText;
	private RelativeLayout lockPasswordChangeBtn;

	public LockSettingLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_setting_lock_password;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		
		Listener l = new Listener();
		
		backBtn = (ImageButton) findViewById(R.id.locksettingBackBtn);
		backBtn.setOnClickListener(l);
		lockToggleBtn = (RelativeLayout) findViewById(R.id.lockToggleBtn);
		lockToggleBtn.setOnClickListener(l);
		lockToggleText = (TextView) findViewById(R.id.lockToggleText);
		lockPasswordChangeBtn = (RelativeLayout) findViewById(R.id.lockPasswordChangeBtn);
		lockPasswordChangeBtn.setOnClickListener(l);
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.locksettingBackBtn:
				callback.onBackClick();
				break;
			case R.id.lockToggleBtn:
				callback.onLockToggleClick();
				break;
			case R.id.lockPasswordChangeBtn:
				callback.onLockPasswordChangeClick();
				break;
			}
		}
		
	}

}
