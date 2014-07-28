package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingLayout extends AbsCTLayout {

	public interface Callback {
		public void onBackClick();
		
		public void onVersionClick();
		public void onPolishClick();
		public void onLogoutClick();
		public void onChangePWClick();
		public void onPromoCodeClick();
		public void onPushAlarmClick();
		public void onLockClick();
		public void onTutorialClick();
		public void onFAQClick();
		public void onUnregisterClick();
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	private ImageButton backBtn;
	
	private RelativeLayout verBtn;
	private RelativeLayout polishBtn;
	private RelativeLayout logoutBtn;
	private RelativeLayout changepwBtn;
	private RelativeLayout promocodeBtn;
	private RelativeLayout pushalarmBtn;
	private RelativeLayout lockBtn;
	private RelativeLayout tutorialBtn;
	private RelativeLayout faqBtn;
	private RelativeLayout unregisterBtn;
	
	private TextView currentVerText;
	private TextView recentVerText;
	private TextView emailText;
	private TextView promocodeText;
	
	
	public SettingLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_setting;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener listener = new Listener();
		
		backBtn = (ImageButton) findViewById(R.id.settingBackBtn);
		backBtn.setOnClickListener(listener);
		verBtn = (RelativeLayout) findViewById(R.id.settingVersionBtn);
		verBtn.setOnClickListener(listener);
		polishBtn = (RelativeLayout) findViewById(R.id.settingPolishBtn);
		polishBtn.setOnClickListener(listener);
		logoutBtn = (RelativeLayout) findViewById(R.id.settingLogoutBtn);
		logoutBtn.setOnClickListener(listener);
		changepwBtn = (RelativeLayout) findViewById(R.id.settingChangePWBtn);
		changepwBtn.setOnClickListener(listener);
		promocodeBtn = (RelativeLayout) findViewById(R.id.settingPromocodeBtn);
		promocodeBtn.setOnClickListener(listener);
		pushalarmBtn = (RelativeLayout) findViewById(R.id.settingPushAlarmBtn);
		pushalarmBtn.setOnClickListener(listener);
		lockBtn = (RelativeLayout) findViewById(R.id.settingLockBtn);
		lockBtn.setOnClickListener(listener);
		tutorialBtn = (RelativeLayout) findViewById(R.id.settingTutorialBtn);
		tutorialBtn.setOnClickListener(listener);
		faqBtn = (RelativeLayout) findViewById(R.id.settingFAQBtn);
		faqBtn.setOnClickListener(listener);
		unregisterBtn = (RelativeLayout) findViewById(R.id.settingUnregisterBtn);
		unregisterBtn.setOnClickListener(listener);
		
		currentVerText = (TextView) findViewById(R.id.settingCurrentVerText);
		recentVerText = (TextView) findViewById(R.id.settingRecentVerText);
		emailText = (TextView) findViewById(R.id.settingEmailText);
		promocodeText = (TextView) findViewById(R.id.settingPromoCodeText);
	}

	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.settingVersionBtn:
				callback.onVersionClick();
				break;
			case R.id.settingPolishBtn:
				callback.onPolishClick();
				break;
			case R.id.settingLogoutBtn:
				callback.onLogoutClick();
				break;
			case R.id.settingChangePWBtn:
				callback.onChangePWClick();
				break;
			case R.id.settingPromocodeBtn:
				callback.onPromoCodeClick();
				break;
			case R.id.settingPushAlarmBtn:
				callback.onPushAlarmClick();
				break;
			case R.id.settingLockBtn:
				callback.onLockClick();
				break;
			case R.id.settingTutorialBtn:
				callback.onTutorialClick();
				break;
			case R.id.settingFAQBtn:
				callback.onFAQClick();
				break;
			case R.id.settingUnregisterBtn:
				callback.onUnregisterClick();
				break;
			default:
				break;
			}
		}
		
	}
	
}
