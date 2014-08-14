package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.main.QALayout.Callback;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;
import android.app.Activity;
import android.os.Bundle;

public class QAActivity extends Activity implements Callback {

	private QALayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		layout = new QALayout(this);
		layout.setCallback(this);

		setContentView(layout.getView());
	}

	private void sendReport() {
		CTJSONSyncTask task = new CTJSONSyncTask();

		task.addHttpParam("userNum", LoginInfo.getInstance(this).getUserNum());
		task.addHttpParam("reportType", 0);
		task.addHttpParam("message", layout.getQADescText());

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
			}
		});
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onConfirmClick() {
		// TODO Auto-generated method stub
		if (layout.getQADescText().length() >= 10)
			sendReport();
		else {
			layout.showErrorDialog(new ErrorResult(0, "문의 내용을 10자 이상 입력해 주세요."));
		}

	}

}
