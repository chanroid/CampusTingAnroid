package kr.co.redstrap.campusting.join;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.join.frag.ConfirmUnivFrag;
import kr.co.redstrap.campusting.join.frag.InfoFrag;
import kr.co.redstrap.campusting.join.frag.NormalJoinFrag;
import kr.co.redstrap.campusting.join.frag.PictureFrag;
import kr.co.redstrap.campusting.join.frag.TermsFrag;
import kr.co.redstrap.campusting.login.UnitedLoginActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.facebook.Session;

public class JoinActivity extends FragmentActivity implements
		JoinLayout.Callback, AbsJoinFrag.Callback {

	private boolean finishFlag = false;

	private int counter;

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	private TermsFrag termsFrag;
	private NormalJoinFrag normalFrag;
	private InfoFrag infoFrag;
	private PictureFrag picFrag;
	private ConfirmUnivFrag confirmunivFrag;

	// 뷰
	public JoinLayout layout;

	public JoinActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		counter = 5;
		
		termsFrag = new TermsFrag();
		termsFrag.setCallback(this);
		normalFrag = new NormalJoinFrag();
		normalFrag.setCallback(this);
		infoFrag = new InfoFrag();
		infoFrag.setCallback(this);
		picFrag = new PictureFrag();
		picFrag.setCallback(this);
		confirmunivFrag = new ConfirmUnivFrag();
		confirmunivFrag.setCallback(this);
		
		// 인디케이터 셋팅
		FragmentPagerAdapter adapter = new JoinFragAdapter(
				getSupportFragmentManager());
		layout = new JoinLayout(this, adapter);
		layout.setCallback(this);
		setContentView(layout.getView());

		// 20140805 chanroid 인텐트 받아서 내정보 입력 부분 조정해 줘야 하는 부분
		if (getIntent().getStringExtra("loginType").equalsIgnoreCase(
				CampusTingConstant.LoginType.FACEBOOK)) {
			
		} else if (getIntent().getStringExtra("loginType").equalsIgnoreCase(
				CampusTingConstant.LoginType.NAVER)) {
			
		}
		// 서비스 시작

	}

	class JoinFragAdapter extends FragmentPagerAdapter {
		public JoinFragAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return termsFrag;
			case 1:
				return normalFrag;
			case 2:
				return infoFrag;
			case 3:
				return picFrag;
			case 4:
				return confirmunivFrag;
			default:
				return termsFrag;
			}
		}

		@Override
		public int getCount() {
			return counter;
		}

	}

	@Override
	public void onBackPressed() {

		if (finishFlag) {
			Session session = Session.getActiveSession();
			if (session != null) {
				session.closeAndClearTokenInformation();
			}
			super.onBackPressed();
		} else {
			finishFlag = true;
			Toast.makeText(MainApp.appContext, "한번 더 누르면 캠퍼스팅을 종료합니다",
					Toast.LENGTH_SHORT).show();

			new Thread() {
				@Override
				public void run() {
					try {
						sleep(1000);
						finishFlag = false;
					} catch (InterruptedException e) {
						Log.i("InterruptedException", e.toString());
					}
				}
			}.start();
		}
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		// 페이스북 세션이 있으면 초기화 시켜줄것
		Session session = Session.getActiveSession();
		if (session != null) {
			session.closeAndClearTokenInformation();
		}

		startActivity(new Intent(this, UnitedLoginActivity.class));
		finish();
	}

	private void changeFragment(int index) {
		if (index > 0 && !termsFrag.isComfirmed()) {
			// 이용약관에 동의
			layout.showErrorDialog(new ErrorResult(0, "이용약관에 동의해주세요."));
			layout.setCurrentPage(0);
		} else if (index > 1 && !normalFrag.isComfirmed()) {
			// 내 정보를 입력
			layout.showErrorDialog(new ErrorResult(0, "내 정보를 입력해주세요."));
			layout.setCurrentPage(1);
		} else if (index > 2 && !infoFrag.isComfirmed()) {
			// 프로필 입력
			layout.showErrorDialog(new ErrorResult(0, "프로필을 입력해주세요."));
			layout.setCurrentPage(2);
		} else if (index > 3 && !picFrag.isComfirmed()) {
			// 사진을 등록
			layout.showErrorDialog(new ErrorResult(0, "사진을 등록해주세요."));
			layout.setCurrentPage(3);
		} else {
			layout.setCurrentPage(index);
		}
	}

	@Override
	public void onTermsClick() {
		// TODO Auto-generated method stub
		// 20140723 chanroid 일단 그냥 변경하는걸로. 액티비티 기능 구현할때 조건 부여
		changeFragment(0);
	}

	@Override
	public void onMyInfoClick() {
		// TODO Auto-generated method stub
		changeFragment(1);
	}

	@Override
	public void onProfileClick() {
		// TODO Auto-generated method stub
		changeFragment(2);
	}

	@Override
	public void onPhotoClick() {
		// TODO Auto-generated method stub
		changeFragment(3);
	}

	@Override
	public void onJobUnivClick() {
		// TODO Auto-generated method stub
		changeFragment(4);
	}

	@Override
	public void goNext(int currentIndex) {
		// TODO Auto-generated method stub
		if (currentIndex < 4)
			changeFragment(currentIndex + 1);
	}
}
