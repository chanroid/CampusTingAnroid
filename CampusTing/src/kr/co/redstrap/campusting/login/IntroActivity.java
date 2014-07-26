package kr.co.redstrap.campusting.login;

import kr.co.redstrap.campusting.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * 로그인 액티비티가 먼저 뜨고 그 위에 인트로가 뜸
 * 
 * @author rbi_bi_3
 *
 */
public class IntroActivity extends Activity {

	public IntroActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 매우 간단한 뷰라 굳이 분리 시키지 않아도 될듯
		setContentView(R.layout.activity_intro);

		// 3초 안에 인트로는 종료됨
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(3000);
					setResult(Activity.RESULT_OK);
					finish();
				} catch (InterruptedException e) {
					Log.i("InterruptedException", e.toString());
				}
			}
		}.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(0, R.anim.fade_out);
	}
}
