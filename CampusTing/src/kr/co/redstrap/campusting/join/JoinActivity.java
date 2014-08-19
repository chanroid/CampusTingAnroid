package kr.co.redstrap.campusting.join;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.protocol.HTTP;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.join.frag.ConfirmUnivFrag;
import kr.co.redstrap.campusting.join.frag.InfoFrag;
import kr.co.redstrap.campusting.join.frag.NormalJoinFrag;
import kr.co.redstrap.campusting.join.frag.PictureFrag;
import kr.co.redstrap.campusting.join.frag.TermsFrag;
import kr.co.redstrap.campusting.join.frag.NormalJoinFrag.NormalJoinInfo;
import kr.co.redstrap.campusting.login.UnitedLoginActivity;
import kr.co.redstrap.campusting.main.MainActivity;
import kr.co.redstrap.campusting.util.SHA256;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;
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
	private InfoFrag infoFrag;
	private PictureFrag picFrag;
	private ConfirmUnivFrag confirmunivFrag;
	public NormalJoinFrag normalFrag;

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
			NormalJoinInfo info = new NormalJoinInfo();
			info.birth = getIntent().getStringExtra("birth");
			info.email = getIntent().getStringExtra("email");
			info.pw = getIntent().getStringExtra("id");
			info.gender = "male".equals(getIntent().getStringExtra("gender"));

			normalFrag.setNormalJoinInfo(info);
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
		else if (currentIndex == 4) {

			CTJSONSyncTask task = new CTJSONSyncTask();

			task.addHttpParam("userId", normalFrag.getInfo().email);
			task.addHttpParam("userPw",
					SHA256.getCipherText(normalFrag.getInfo().pw));
			task.addHttpParam("gender",
					String.valueOf(normalFrag.getInfo().gender));
			task.addHttpParam("birth", normalFrag.getInfo().birth);
			task.addHttpParam("promoCode", normalFrag.getInfo().promoCode);
			task.addHttpParam("local", infoFrag.local);
			task.addHttpParam("character", infoFrag.characterConstText);
			task.addHttpParam("body", infoFrag.body);
			task.addHttpParam("height", infoFrag.height);
			task.addHttpParam("bloodType", infoFrag.bloodType);
			task.addHttpParam("religion", infoFrag.religion);
			task.addHttpParam("smoke", String.valueOf(infoFrag.smoke));
			task.addHttpParam("drink", infoFrag.drink);
			task.addHttpParam("major", infoFrag.major);
			task.addHttpParam("coupleCount", infoFrag.coupleCount);
			task.addHttpParam("univMail", confirmunivFrag.univMail);
			task.addHttpParam("univState", confirmunivFrag.univState);

			task.addHttpFileParam("univCardPhoto",
					confirmunivFrag.univCardImage);
			task.addHttpFileParam("jobPhoto", confirmunivFrag.jobImage);
			for (int i = 0; i < picFrag.profileImageList.size(); i++) {
				task.addHttpFileParam("profilePhoto" + (i + 1),
						picFrag.profileImageList.get(i));
			}

			try {
				task.addHttpParam("job",
						URLEncoder.encode(infoFrag.job, HTTP.UTF_8));
				task.addHttpParam("nickName", URLEncoder.encode(
						normalFrag.getInfo().nickName, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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

					// 20140723 chanroid 일단 그냥 메인으로 넘어가게 설정. 나중에 액티비티 구현할때 마저 구현
					startActivity(new Intent(JoinActivity.this,
							MainActivity.class));
					finish();
				}
			});

			task.executeParallel("userPost");
		}
	}
}
