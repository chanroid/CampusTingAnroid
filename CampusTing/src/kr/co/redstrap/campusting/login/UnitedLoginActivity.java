package kr.co.redstrap.campusting.login;

import java.util.Arrays;
import java.util.List;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.common.SimpleTextWatcher;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.constant.CampusTingConstant.LoginType;
import kr.co.redstrap.campusting.constant.CampusTingConstant.RequestCodes;
import kr.co.redstrap.campusting.join.JoinActivity;
import kr.co.redstrap.campusting.main.MainActivity;
import kr.co.redstrap.campusting.util.DayUtil;
import kr.co.redstrap.campusting.util.PwInputFilter;
import kr.co.redstrap.campusting.util.SHA256;
import kr.co.redstrap.campusting.util.ViewUtil;
import kr.co.redstrap.campusting.util.indicator.TabPageIndicator;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

public class UnitedLoginActivity extends FragmentActivity implements
		CTSyncTaskCallback<String, Object> {
	/**
	 * Fragment를 사용하지 않는데 왜 FragmentActivity를 사용하는지 의문
	 */

	private boolean finishFlag = false; // 두번 눌러야 종료
	private boolean loginFlag = false; // 로그인 인터페이스 표시 여부
	private boolean introEndFlag = false;
	public TabPageIndicator indicator;
	private LoginEditorActionListener loginEditorActionListener;
	private LoginClickListener loginClickListener;

	private CTJSONSyncTask getuserTask;

	private UnitedLoginLayout layout;

	private OAuthLogin oAuthLoginInstance;

	private List<String> fbPermissions = Arrays
			.asList("email", "user_about_me");

	public UnitedLoginActivity() {
		super();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("onCreate", "onCreate");

		loginEditorActionListener = new LoginEditorActionListener();
		loginClickListener = new LoginClickListener();

		layout = new UnitedLoginLayout(this);
		layout.setClickListener(loginClickListener);
		layout.setEditorListener(loginEditorActionListener);
		layout.setDay(DayUtil.isDay());
		layout.setPwInputFilters(new InputFilter[] { new PwInputFilter() });
		layout.addIdTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				layout.changeInputIdIcon(ViewUtil.isTypeEmail(s.toString()));
			}
		});

		setContentView(layout.getView());

		// 인트로 시작
		introStart();

		// 페이스북 로그인 관련 데이터 세팅
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback,
						savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
		}
		
		// 네이버 로그인 관련 데이터 세팅
		oAuthLoginInstance = OAuthLogin.getInstance();
		oAuthLoginInstance.init(this, "HqHCKMGfh5WrSrZEV1R7", "fN56Rd7iQr", "캠퍼스팅", "http://www.redstrap.co.kr");
		layout.setNaverloginHandler(mOAuthLoginHandler);

		if (LoginInfo.getInstance(this).isAutoLogin()) {
			loginFlag = false;
			LoginInfo info = LoginInfo.getInstance(this);
			layout.inputId.setText(info.getUserId());
			layout.inputPw.setText(info.getUserPw());
		} else {
			loginFlag = true;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("onActivityResult", requestCode + " :: " + resultCode + " :: "
				+ data);

		// 로그인 체크
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == RequestCodes.LOGIN_FACEBOOK) { // 페이스북
				Session.getActiveSession().onActivityResult(this, requestCode,
						resultCode, data);
			} else if (requestCode == CampusTingConstant.RequestCodes.INTRO_REQUEST) {
				introEndFlag = true;
				layout.showLoginViews();
				if (loginFlag) {
					finishActivity(CampusTingConstant.RequestCodes.INTRO_REQUEST);
				} else { // 자동 로그인
					normalLoginAction();
				}
			}
		} else {
			if (requestCode == CampusTingConstant.RequestCodes.INTRO_REQUEST) {
				finish();
			} else if (requestCode == 64206) {

			}
		}
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
			layout.toast(R.string.exit_confirm, Toast.LENGTH_SHORT);

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

	/**
	 * 인트로 액티비티 실행
	 */
	private void introStart() {
		Intent introIntent = new Intent(MainApp.appContext,
				kr.co.redstrap.campusting.login.IntroActivity.class);
		startActivityForResult(introIntent,
				CampusTingConstant.RequestCodes.INTRO_REQUEST);
	}

	/**
	 * 페이스북 로그인 성공시 할 작업
	 * 
	 * @param user
	 *            {@link GraphUserCallback#onCompleted(GraphUser, Response)}
	 * @param response
	 *            {@link GraphUserCallback#onCompleted(GraphUser, Response)}
	 */
	private void facebookLoginAction(final GraphUser user, Response response) {

		final String email = (String) user.getProperty("email");
		final String id = user.getId();

		CTJSONSyncTask task = new CTJSONSyncTask();
		task.addCallback(new CTSyncTaskCallback.Stub() {
			@Override
			public void onErrorTask(AbsCTSyncTask<String, Object> task,
					ErrorResult error) {
				// TODO Auto-generated method stub
				layout.dismissLoading();
				if (error.message.contains("없는")) {
					// 20140818 chanroid 없는 유저 경우 회원가입으로 넘어가게. 차후 코드구분으로 변경해야 함

					Toast.makeText(UnitedLoginActivity.this,
							"이 아이디로 처음 로그인 하셨어요. 회원가입 진행 후 사용이 가능합니다.",
							Toast.LENGTH_LONG).show();

					String gender = (String) user.getProperty("gender");
					String birth = user.getBirthday();

					Intent joinIntent = new Intent(UnitedLoginActivity.this,
							JoinActivity.class);
					joinIntent.putExtra("email", email);
					joinIntent.putExtra("id", id);
					joinIntent.putExtra("gender", gender);
					joinIntent.putExtra("birth", birth);
					joinIntent.putExtra("loginType", LoginType.FACEBOOK);

					startActivity(joinIntent);
					finishActivity(CampusTingConstant.RequestCodes.INTRO_REQUEST);
					finish();

				} else {
					layout.showErrorDialog(error);
					layout.swapLoginInterface();
				}
			}

			@Override
			public void onStartTask(AbsCTSyncTask<String, Object> task) {
				// TODO Auto-generated method stub
				layout.showLoading(null);
			}

			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object result) {
				// TODO Auto-generated method stub
				UnitedLoginActivity.this.onSuccessTask(task, result);
			}
		});

		task.addHttpParam("userId", email);
		task.addHttpParam("userPw", id);
		task.addHttpParam("pushKey", "12312312312313"); // 20140808
														// chanroid test

		task.executeParallel("login");
	}

	/**
	 * 일반 로그인시 할 작업
	 */
	private void normalLoginAction() {

		String userId;
		String userPw;

		if (loginFlag) { // 자동 로그인이 아닌 경우
			userId = layout.inputId.getText().toString();
			userPw = SHA256.getCipherText(layout.inputPw.getText().toString());
		} else {
			LoginInfo info = LoginInfo.getInstance(this);
			userId = info.getUserId();
			userPw = info.getUserPw();
		}

		getuserTask = new CTJSONSyncTask();
		getuserTask.addCallback(this);

		getuserTask.addHttpParam("userId", userId);
		getuserTask.addHttpParam("userPw", userPw);
		getuserTask.addHttpParam("pushKey", "12312312312313"); // 20140808
																// chanroid test

		getuserTask.executeSerial("login");
	}

	private void settingMainUserInfo(JSONObject result) throws JSONException {

		LoginInfo info = LoginInfo.getInstance(this);

		info.setAcEmail(result.getString("univMail"));
		info.setUserNum(result.getInt("userNum"));
		info.setNickName(result.getString("nickName"));
		info.setMajorNum(result.getInt("majorNum"));
		info.setRegDate(result.getString("regDate"));
		info.setPhotoCount(result.getInt("photoCount"));
		info.setIsAccept("N".equals(result.getString("isAccept")) ? false
				: true);
		info.setUnivNum(result.getInt("univNum"));

		// 20140805 chanroid 아직 덜 작성함;;

	}

	/**
	 * 키보드 액션 버튼 리스너
	 * 
	 * @author play
	 * 
	 */
	private class LoginEditorActionListener implements OnEditorActionListener {

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			switch (v.getId()) {
			case R.id.input_id:
				if (ViewUtil.isTypeEmail(v.getEditableText().toString())) {
					return false; // 액션 작동
				} else {
					layout.toast(R.string.error_email_confirm,
							Toast.LENGTH_SHORT);
					layout.changeInputIdIcon(false);
					return true; // 액션 작동 안함
				}
			case R.id.input_pw:
				layout.performConfirm();
				return false;
			}

			return true; // 액션 멈춤
		}
	}

	/**
	 * 버튼 클릭 리스너
	 * 
	 * @author play
	 * 
	 */
	private class LoginClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			ViewUtil.filterClick(view);

			switch (view.getId()) {
			case R.id.btn_normal_login_cancel:
				layout.swapLoginInterface();
				finishActivity(CampusTingConstant.RequestCodes.INTRO_REQUEST);
				break;
			case R.id.btn_normal_login_confirm:
				// 로그인 버튼 클릭시
				// 이메일 유효성과 비밀번호 입력 여부 확인

				if (layout.inputPw.getText().toString().length() < 6) { // 비밀번호
																		// 자릿수
					layout.toast(R.string.error_password_confirm,
							Toast.LENGTH_SHORT);
					layout.inputPw.requestFocus();
				} else if (!ViewUtil.isTypeEmail(layout.inputId.getText()
						.toString())) { // 유효하지 않은 아이디
					layout.toast(R.string.error_email_confirm,
							Toast.LENGTH_SHORT);
					layout.inputId.setCompoundDrawablesWithIntrinsicBounds(
							null, null, null, null);
					layout.inputId.requestFocus();
				} else {
					layout.inputId.setCompoundDrawablesWithIntrinsicBounds(
							null, null, null, null);
					normalLoginAction();
				}
				break;
			case R.id.btn_normal_join:
				Intent joinIntent = new Intent(MainApp.appContext,
						kr.co.redstrap.campusting.join.JoinActivity.class);
				joinIntent.putExtra("loginType", LoginType.CAMPUSTING);
				startActivity(joinIntent);
				finish();
				break;
			case R.id.btn_find_pw:
				break;
			case R.id.btn_naver:
				onNaverLoginClick();
				break;
			case R.id.btn_facebook:
				onFacebookLoginClick();
				break;
			case R.id.btn_normal_login:
				layout.swapLoginInterface();
				finishActivity(CampusTingConstant.RequestCodes.INTRO_REQUEST);
				break;
			default:
				break;
			}

		}

	}

	private void onNaverLoginClick() {
		oAuthLoginInstance.startOauthLoginActivity(this, mOAuthLoginHandler);
	}

	/**
	 * startOAuthLoginActivity() 호출시 인자로 넘기거나, OAuthLoginButton 에 등록해주면 인증이 종료되는
	 * 걸 알 수 있다.
	 */
	private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
		@Override
		public void run(boolean success) {
			if (success) {
				// 20140819 chanroid 현재 콜백 url 에러로 인해 해당 부분으로 넘어오지 않음
				String accessToken = oAuthLoginInstance
						.getAccessToken(UnitedLoginActivity.this);
				String refreshToken = oAuthLoginInstance
						.getRefreshToken(UnitedLoginActivity.this);
				long expiresAt = oAuthLoginInstance
						.getExpiresAt(UnitedLoginActivity.this);
				String tokenType = oAuthLoginInstance
						.getTokenType(UnitedLoginActivity.this);
				Log.i("Naver", "success : " + accessToken + ", " + refreshToken + ", " + expiresAt + ", " + tokenType);
				
				new NaverRequestApiTask().execute();
			} else {
				String errorCode = oAuthLoginInstance.getLastErrorCode(
						UnitedLoginActivity.this).getCode();
				String errorDesc = oAuthLoginInstance
						.getLastErrorDesc(UnitedLoginActivity.this);
				Log.i("Naver", "errorCode:" + errorCode + ", errorDesc:" + errorDesc);
				layout.showErrorDialog(new ErrorResult(0, "네이버 아이디 로그인에 실패했습니다. " + errorDesc));
			}
		};
	};
	
	private class NaverRequestApiTask extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			layout.showLoading(null);
		}
		@Override
		protected String doInBackground(Void... params) {
			String url = "https://apis.naver.com/nidlogin/nid/getHashId_v2.xml";
			String at = oAuthLoginInstance.getAccessToken(UnitedLoginActivity.this);
			return oAuthLoginInstance.requestApi(UnitedLoginActivity.this, at, url);
		}
		protected void onPostExecute(String content) {
			layout.dismissLoading();
			Log.d("Naver", "requestApi result : " + content);
			// 20140819 chanroid 아이디값 가져와서 프로필 받아오는 부분으로 넘어가야 함
			new NaverProfileApiTask().execute();
		}
	}
	
	private class NaverProfileApiTask extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			layout.showLoading(null);
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String url = "https://apis.naver.com/nidlogin/nid/getUserProfile.xml";
			String at = oAuthLoginInstance.getAccessToken(UnitedLoginActivity.this);
			return oAuthLoginInstance.requestApi(UnitedLoginActivity.this, at, url);
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			layout.dismissLoading();
			Log.d("Naver", "userProfile result : " + result);
		}
		
	}

	private void onFacebookLoginClick() {
		// TODO Auto-generated method stub
		Session session = Session.getActiveSession();
		if (session != null && !session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this).setPermissions(
					fbPermissions).setCallback(statusCallback));
		} else {
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			// TODO Auto-generated method stub
			if (session.isOpened()) {
				String accessToken = session.getAccessToken();
				Log.i("Facebook", "accessToken : " + accessToken);

				layout.showLoading(null);
				Request.newMeRequest(session, new GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// TODO Auto-generated method stub
						layout.dismissLoading();
						if (user == null)
							return;
						Log.i("Facebook", "user : " + user.getInnerJSONObject());
						facebookLoginAction(user, response);     
					}
				}).executeAsync();
			} else {
				// 에러
				Log.i("Facebook", "login error");
			}
		}
	};

	@Override
	public void onStartTask(AbsCTSyncTask<String, Object> task) {
		// TODO Auto-generated method stub
		layout.showLoading(null);
	}

	@Override
	public void onProgressTask(AbsCTSyncTask<String, Object> task, int progress) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onErrorTask(AbsCTSyncTask<String, Object> task,
			ErrorResult error) {
		// TODO Auto-generated method stub
		layout.dismissLoading();
		layout.showErrorDialog(error);
		LoginInfo.getInstance(this).setAutoLogin(false);
		layout.swapLoginInterface();
	}

	@Override
	public void onSuccessTask(AbsCTSyncTask<String, Object> task, Object result) {
		// TODO Auto-generated method stub
		layout.dismissLoading();

		JSONObject resultJSON = (JSONObject) result;

		if (result != null) { // 우선은 결과값이 뭐라도 있으면 로그인 성공으로 간주
			try {
				settingMainUserInfo(resultJSON);

				LoginInfo info = LoginInfo.getInstance(this);
				if (loginFlag) {
					info.setUserId(layout.inputId.getText().toString());
					info.setUserPw(SHA256.getCipherText(layout.inputPw
							.getText().toString()));
					info.setAutoLogin(true);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				// 로그인 실패시 따로 처리하는 부분 구현해야 함
			}
			if (introEndFlag) { // 인트로 이후의 수동 로그인일 경우 직접 메인 이동
				Intent mianIntent = new Intent(MainApp.appContext,
						MainActivity.class);
				startActivity(mianIntent);
				finish();
			}
		} else {
			layout.toast(R.string.error_login_fail, Toast.LENGTH_SHORT);
		}
	}
}
