package kr.co.redstrap.campusting.setting;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;
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
		layout.setEmailIdText(LoginInfo.getInstance(this).getUserId());
		loadPromoCode();
		
		setContentView(layout.getView());
		
	}
	
	private void loadPromoCode() {
		CTJSONSyncTask task = new CTJSONSyncTask();
		
		task.addHttpParam("userNum", LoginInfo.getInstance(this).getUserNum());
		
		task.addCallback(new CTSyncTaskCallback<String, Object>() {

			@Override
			public void onStartTask(AbsCTSyncTask<String, Object> task) {
				// TODO Auto-generated method stub
				layout.showLoading(null);
			}

			@Override
			public void onProgressTask(AbsCTSyncTask<String, Object> task,
					int progress) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onErrorTask(AbsCTSyncTask<String, Object> task,
					ErrorResult error) {
				// TODO Auto-generated method stub
				layout.dismissLoading();
				layout.showErrorDialog(error);
			}

			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object result) {
				// TODO Auto-generated method stub
				layout.dismissLoading();
				
				JSONObject resultJSON = (JSONObject) result;
				try {
					String promoCode = resultJSON.getString("promoCode");
					layout.setPromoCodeText(promoCode);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		task.executeParallel("promoCodePost");
	}
	
	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onVersionClick() {
		// TODO Auto-generated method stub
		// 20140811 chanroid 업데이트 있을 때만 실행
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
		// 20140811 chanroid 클릭하면 클립보드로 복사되는 기능 있으면 좋을것 같다
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
