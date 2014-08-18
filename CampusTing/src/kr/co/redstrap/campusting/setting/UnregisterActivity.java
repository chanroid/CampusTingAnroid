package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class UnregisterActivity extends FragmentActivity implements
		UnregisterLayout.Callback {

	private UnregisterLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		layout = new UnregisterLayout(this);
		layout.setCallback(this);

		setContentView(layout.getView());
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onRestClick() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this).setMessage("휴학 신청을 하시겠습니까?")
				.setPositiveButton("예", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						unregisterAction(false);
					}
				}).setNegativeButton("아니오", null).show();
	}

	@Override
	public void onUnregisterClick() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this).setMessage("탈퇴하시겠습니까?")
				.setPositiveButton("예", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						unregisterAction(true);
					}
				}).setNegativeButton("아니오", null).show();
	}

	private void unregisterAction(boolean flag) {
		CTJSONSyncTask task = new CTJSONSyncTask();
		task.addHttpParam("userNum", LoginInfo.getInstance(this).getUserNum());
		task.addHttpParam("isUnregister", String.valueOf(flag));
		task.addHttpParam("reason", 0);
		task.addHttpParam("desc", "");
		// 20140818 chanroid 사유는 flow chart에 없으므로 일단은 비워둠

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
				setResult(RESULT_OK);
				finish();
			}
		});

		task.execute("userDelete");
	}
}
