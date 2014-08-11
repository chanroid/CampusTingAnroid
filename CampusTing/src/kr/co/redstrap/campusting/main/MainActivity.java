package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.login.UnitedLoginActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		MainLayout.Callback, CampusTingConstant {
	private boolean finishFlag = false; // 두번 눌러야 종료

	private MainLayout layout;

	private Fragment mainFrag;
	private Fragment idCardFrag;
	private Fragment moreFrag;
	private Fragment historyFrag;
	private Fragment chatFrag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		layout = new MainLayout(this);
		layout.setCallback(this);

		setContentView(layout.getView());

		// TODO 마지막 접속일 저장
		// TODO 모바일 접속일 경우 모바일 고유번호와 모바일 종류 확인 변경됐을 경우 다시 설정

		Intent startMode = getIntent();
		if (startMode != null) { // 새로 가입한 사람은 자기 학생증을 띄워줌
			boolean isBiginner = startMode.getBooleanExtra("isBiginner", false);
			Log.i("startMode", "isBiginner : " + isBiginner);
			if (isBiginner) {
				Intent idIntent = new Intent(this,
						kr.co.redstrap.campusting.common.IdActivity.class);
				startActivity(idIntent);
			}
		}

		initFragment();
		switchFragment(mainFrag);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case RequestCodes.SETTING:
			switch (arg1) {
			case RESULT_CANCELED:
				finish();
				startActivity(new Intent(this, UnitedLoginActivity.class));
				break;
			}
			break;
		}
	}

	private void initFragment() {
		mainFrag = new MainFragment();
		idCardFrag = new IdCardFragment();
		moreFrag = new MoreFragment();
		historyFrag = new HistoryFragment();
		chatFrag = new ChatFragment();

		FragmentTransaction trans = getSupportFragmentManager()
				.beginTransaction();
		trans.add(R.id.mainFragmentContainer, mainFrag);
		trans.add(R.id.mainFragmentContainer, idCardFrag);
		trans.add(R.id.mainFragmentContainer, moreFrag);
		trans.add(R.id.mainFragmentContainer, historyFrag);
		trans.add(R.id.mainFragmentContainer, chatFrag);
		trans.commit();
	}

	private void switchFragment(Fragment fragment) {
		FragmentTransaction trans = getSupportFragmentManager()
				.beginTransaction();
		trans.hide(mainFrag);
		trans.hide(idCardFrag);
		trans.hide(moreFrag);
		trans.hide(historyFrag);
		trans.hide(chatFrag);

		trans.show(fragment);
		trans.commit();
	}
	
	public void switchFragment(int index) {
		switch (index) {
		case 0:
			switchFragment(idCardFrag);
			break;
		case 1:
			switchFragment(historyFrag);
			break;
		case 2:
			switchFragment(mainFrag);
			break;
		case 3:
			switchFragment(chatFrag);
			break;
		case 4:
			switchFragment(moreFrag);
			break;
		}
		layout.switchButtonBackground(index);
	}

	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(R.anim.fade_in, 0);
	}

	@Override
	public void onBackPressed() {

		if (finishFlag) {
			super.onBackPressed();
		} else {
			finishFlag = true;
			Toast.makeText(MainApp.appContext, R.string.exit_confirm,
					Toast.LENGTH_SHORT).show();

			new Thread() {
				@Override
				public void run() {
					try {
						sleep(1000);
						finishFlag = false;
					} catch (InterruptedException e) {
						Log.e("InterruptedException", e.toString());
					}
				}
			}.start();
		}
	}

	@Override
	public void onIdCardClick() {
		// TODO Auto-generated method stub
		switchFragment(idCardFrag);
	}

	@Override
	public void onHistoryClick() {
		// TODO Auto-generated method stub
		switchFragment(historyFrag);
	}

	@Override
	public void onMainClick() {
		// TODO Auto-generated method stub
		switchFragment(mainFrag);
	}

	@Override
	public void onChatClick() {
		// TODO Auto-generated method stub
		switchFragment(chatFrag);
	}

	@Override
	public void onMoreClick() {
		// TODO Auto-generated method stub
		switchFragment(moreFrag);
	}

}
