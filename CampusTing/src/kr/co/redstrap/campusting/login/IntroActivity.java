package kr.co.redstrap.campusting.login;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.util.DayUtil;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

/**
 * 로그인 액티비티가 먼저 뜨고 그 위에 인트로가 뜸
 * 
 * @author rbi_bi_3
 * 
 */
public class IntroActivity extends Activity {

	private IntroLayout layout;

	public IntroActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layout = new IntroLayout(this);
		layout.setDay(DayUtil.isDay());

		setContentView(layout.getView());

		loadBaseInfo();
	}

	private void loadBaseInfo() {
		// TODO Auto-generated method stub
		CTJSONSyncTask task = new CTJSONSyncTask();

		task.addCallback(new CTSyncTaskCallback.Stub() {
			@Override
			public void onErrorTask(AbsCTSyncTask<String, Object> task,
					ErrorResult error) {
				// TODO Auto-generated method stub
				layout.showErrorDialog(error, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						setResult(RESULT_CANCELED);
						finish();
					}
				});
			}

			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object result) {
				// TODO Auto-generated method stub

				JSONObject resultJSON = (JSONObject) result;
				try {
					int dbVer = resultJSON.getInt("dbVer");
					String domain = resultJSON.getString("mainDomain");
//					String appVer = resultJSON.getString("appVer");
//					boolean forceUpdate = resultJSON.getBoolean("forceUpdate");

					setResult(RESULT_OK);
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					layout.showErrorDialog(ErrorResult.resultFromException(e),
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									setResult(RESULT_CANCELED);
									finish();
								}
							});
				}
			}
		});

		task.execute("baseInformation");
	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(0, R.anim.fade_out);
	}
}
