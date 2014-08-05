package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.common.LoginInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class SettingActivity extends Activity implements SettingLayout.Callback {
	
	private SettingLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new SettingLayout(this);
		layout.setCallback(this);
		setContentView(layout.getView());
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onVersionClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPolishClick() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, PolishActivity.class));
	}

	@Override
	public void onLogoutClick() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
		.setTitle("로그아웃")
		.setMessage("로그아웃 하시겠습니까?")
		.setPositiveButton("예", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				setResult(RESULT_CANCELED);
				LoginInfo info = LoginInfo.getInstance(SettingActivity.this);
				info.setAutoLogin(false); // 로그인 정보 알아서 초기화됨
				finish();
			}
		})
		.setNegativeButton("아니오", null);
		builder.show();
	}

	@Override
	public void onChangePWClick() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, ChangePWActivity.class));
	}

	@Override
	public void onPromoCodeClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPushAlarmClick() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, PushAlarmActivity.class));
	}

	@Override
	public void onLockClick() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, LockSettingActivity.class));
	}

	@Override
	public void onTutorialClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFAQClick() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, FaqActivity.class));
	}

	@Override
	public void onUnregisterClick() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, UnregisterActivity.class));
	}
}
