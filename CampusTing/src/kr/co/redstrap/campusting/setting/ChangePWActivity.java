package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.util.SHA256;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class ChangePWActivity extends FragmentActivity implements
		ChangePWLayout.Callback {

	private ChangePWLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		layout = new ChangePWLayout(this);
		layout.setCallback(this);

		setContentView(layout.getView());
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onChangePWClick() {
		// TODO Auto-generated method stub
		if (layout.getChangePW() == null)
			layout.toast("패스워드를 확인해주세요", Toast.LENGTH_LONG);
		else
			requestChangePw();
	}

	private void requestChangePw() {
		CTJSONSyncTask task = new CTJSONSyncTask();

		task.addHttpParam("userNum", LoginInfo.getInstance(this).getUserNum());
		task.addHttpParam("orignPw",
				SHA256.getCipherText(layout.getCurrentPW()));
		task.addHttpParam("changePw",
				SHA256.getCipherText(layout.getChangePW()));

		task.addCallback(new CTSyncTaskCallback.Stub() {
			@Override
			public void onStartTask(AbsCTSyncTask<String, Object> task) {
				// TODO Auto-generated method stub
				super.onStartTask(task);
				layout.showLoading(null);
			}

			@Override
			public void onErrorTask(AbsCTSyncTask<String, Object> task,
					ErrorResult error) {
				// TODO Auto-generated method stub
				super.onErrorTask(task, error);
				layout.dismissLoading();
				layout.showErrorDialog(error);
			}

			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object result) {
				// TODO Auto-generated method stub
				super.onSuccessTask(task, result);
				layout.dismissLoading();

				layout.toast("비밀번호 변경이 완료되었습니다.", Toast.LENGTH_LONG);
				LoginInfo.getInstance(ChangePWActivity.this).setUserPw(
						SHA256.getCipherText(layout.getChangePW()));
				finish();
			}
		});

		task.executeParallel("passwordPut");
	}
}
