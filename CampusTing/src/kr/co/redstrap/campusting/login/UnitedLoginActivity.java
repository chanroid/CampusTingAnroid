package kr.co.redstrap.campusting.login;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.deprecated.WebOneCharTask;
import kr.co.redstrap.campusting.deprecated.WebOneJsonTask;
import kr.co.redstrap.campusting.util.PwInputFilter;
import kr.co.redstrap.campusting.util.SHA256;
import kr.co.redstrap.campusting.util.ViewUtil;
import kr.co.redstrap.campusting.util.WrittingUtil;
import kr.co.redstrap.campusting.util.indicator.TabPageIndicator;
import kr.co.redstrap.campusting.util.web.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class UnitedLoginActivity extends FragmentActivity {
	/**
	 * Fragment를 사용하지 않는데 왜 FragmentActivity를 사용하는지 의문
	 */

	private boolean finishFlag = false; // 두번 눌러야 종료
	private boolean loginFlag = false; // 로그인 인터페이스 표시 여부
	private boolean introEndFlag = false;
	public TabPageIndicator indicator;
	private LoginEditorActionListener loginEditorActionListener;
	private LoginClickListener loginClickListener;

	private UnitedLoginLayout layout;
	
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
		layout.setPwInputFilters(new InputFilter[] { new PwInputFilter() });
		layout.addIdTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				layout.changeInputIdIcon(isTypeEmail(s.toString()));
			}
		});
		
		setContentView(layout.getView());

		// 인트로 시작
		introStart();

		// 변수 셋팅
		Log.i("user", MainApp.mainUser.toString());

		// 페이스북 자동 로그인 체크
		if (Session.openActiveSessionFromCache(this) != null) { // 캐쉬에 페이스북 로그인 정보 있음
			Log.i("캐쉬 페이스북 세션 있음", Session.getActiveSession().toString());
			Request.newMeRequest(Session.openActiveSessionFromCache(this), new GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						facebookLoginAction(user, response);
					} else {
						layout.toast(R.string.error_connection, Toast.LENGTH_SHORT);
						loginFlag = true;
					}
				}
			}).executeAsync();
		// } 
		// else if (false) {
		// 캐쉬에 네이버 로그인 정보 있음
			
		} else if (WrittingUtil.getInstance(MainApp.appContext).loadObject("user") != null) { // 캐쉬에 자체 로그인 정보 있음
			// 20140701 테스트용 자동로그인 방지. 해제시 아래 코드 주석 해제
//			MainApp.mainUser = (User) WrittingUtil.getInstance(MainApp.appContext).loadObject("user");
//			Log.i("일반 로그인", MainApp.mainUser.toString());
//			normalLoginAction();
			loginFlag = true;
		} else { // 캐쉬에 저장된 로그인 정보 없음
			loginFlag = true;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("onActivityResult", requestCode + " :: " + resultCode + " :: " + data);

		// 로그인 체크
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 64206) { // 페이스북
				Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
			} else if (requestCode == CampusTingConstant.RequestCodes.INTRO_REQUEST) {
				introEndFlag = true;
				if (loginFlag) {
					layout.showLoginViews();
					finishActivity(CampusTingConstant.RequestCodes.INTRO_REQUEST);
				} else { // 자동 로그인
					Intent mianIntent = new Intent(MainApp.appContext, kr.co.redstrap.campusting.main.MainActivity.class);
					startActivity(mianIntent);
					finish();
				}
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
		Intent introIntent = new Intent(MainApp.appContext, kr.co.redstrap.campusting.login.IntroActivity.class);
		startActivityForResult(introIntent, CampusTingConstant.RequestCodes.INTRO_REQUEST);
	}

	/**
	 * 페이스북 로그인 성공시 할 작업
	 * 
	 * @param user
	 *            {@link GraphUserCallback#onCompleted(GraphUser, Response)}
	 * @param response
	 *            {@link GraphUserCallback#onCompleted(GraphUser, Response)}
	 */
	private void facebookLoginAction(GraphUser user, Response response) {
		try {
			MainApp.mainUser.setUserLoginId(user.getInnerJSONObject().getString("id"));
			MainApp.mainUser.setLoginTypeId(CampusTingConstant.LoginType.FACEBOOK);
			Log.i("user", MainApp.mainUser.toString());

			// TODO
			char result = '!';

			// 가입 여부 체크
			WebOneCharTask<Void, Void> task = new WebOneCharTask<Void, Void>("isUserAlready") {

				@Override
				protected void onPostExecute(Character result) {
					switch (result) {
					case '0': // 가입하지 않은 유저
						Intent joinIntent = new Intent(MainApp.appContext, kr.co.redstrap.campusting.join.JoinActivity.class);
						startActivity(joinIntent);
						finish();
						break;
					case '1': // 이미 가입한 유저
						// 웹에서 유저 정보 받아온 후 맵핑
						WebOneJsonTask<Void, Void> getUserTask = new WebOneJsonTask<Void, Void>("getUser") {

							@Override
							protected void onPostExecute(JSONObject result) {
								result = mainUserTestValue();
								if (result != null) {
									try {
										settingMainUserInfo(result);
										Log.i("페이스북 로그인 유저", MainApp.mainUser.toString());
									} catch (JSONException e) {
										e.printStackTrace();
									}
									if (introEndFlag) { // 인트로 이후의 수동 로그인일 경우 직접 메인 이동
										Intent mianIntent = new Intent(MainApp.appContext, kr.co.redstrap.campusting.main.MainActivity.class);
										startActivity(mianIntent);
										finish();
									}
								}
							}

						};
						getUserTask.put("userLoginId", MainApp.mainUser.getUserLoginId());
						getUserTask.put("loginTypeId", MainApp.mainUser.getLoginTypeId());
						getUserTask.executeSerial();
						break;
					default: // 웹서버 연결 과정에서 에러 발생
						layout.toast(R.string.error_connection, Toast.LENGTH_SHORT);
						loginFlag = true;
						break;
					}
				}
			};
			task.put("userLoginId", MainApp.mainUser.getUserLoginId());
			task.put("loginTypeId", MainApp.mainUser.getLoginTypeId());
			task.executeSerial();

		} catch (Exception e) {
			layout.toast(R.string.error_connection, Toast.LENGTH_SHORT);
			Log.e("에러", e.toString());
		}
	}

	/**
	 * 일반 로그인시 할 작업
	 */
	private void normalLoginAction() {
		if (loginFlag) { // 자동 로그인이 아닌 경우
			MainApp.mainUser.setLoginTypeId(CampusTingConstant.LoginType.CAMPUSTING);
			MainApp.mainUser.setUserLoginId(layout.inputId.getEditableText().toString());
			MainApp.mainUser.setPw(SHA256.getCipherText(layout.inputPw.getEditableText().toString())); // 비밀번호는 암호화하여 저장
		}
		WebOneJsonTask<Void, Void> getUserTask = new WebOneJsonTask<Void, Void>("getUser") {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				layout.showLoading("Loading...");
			}
			
			@Override
			protected void onPostExecute(JSONObject result) {
				
				result = mainUserTestValue();
				
				if (result != null) { // 우선은 결과값이 뭐라도 있으면 로그인 성공으로 간주
					try {
						settingMainUserInfo(result);
						Log.i("일반 로그인 유저", MainApp.mainUser.toString());
					} catch (JSONException e) {
						e.printStackTrace();
						// 로그인 실패시 따로 처리하는 부분 구현해야 함
					}
					WrittingUtil.getInstance(MainApp.appContext).saveObject("user", MainApp.mainUser); // 저장된 user 객체 갱신
					if (introEndFlag) { // 인트로 이후의 수동 로그인일 경우 직접 메인 이동
						Intent mianIntent = new Intent(MainApp.appContext, kr.co.redstrap.campusting.main.MainActivity.class);
						startActivity(mianIntent);
						finish();
					}
				} else {
					layout.toast(R.string.error_login_fail, Toast.LENGTH_SHORT);
				}
			}

		};
		getUserTask.put("userLoginId", MainApp.mainUser.getUserLoginId());
		getUserTask.put("loginTypeId", MainApp.mainUser.getLoginTypeId());
		getUserTask.put("pw", MainApp.mainUser.getPw());
		getUserTask.executeSerial();
	}
	
	private void settingMainUserInfo(JSONObject result) throws JSONException {
		MainApp.mainUser.setAcEmail(result.getString("acEmail"));
		MainApp.mainUser.setJoinDate(result.getString("joinDate"));
		MainApp.mainUser.setPictureNum(result.getString("pictureNum"));
		MainApp.mainUser.setMainPicture(result.getString("mainPicture"));
		MainApp.mainUser.setLoginTypeId(result.getString("loginTypeId"));
		MainApp.mainUser.setJudge(result.getString("judge"));
		MainApp.mainUser.setTermsDate(result.getString("termsDate"));
		MainApp.mainUser.setUserId(result.getString("userId"));
		MainApp.mainUser.setUserLoginId(result.getString("userLoginId"));
		MainApp.mainUser.setPw(result.getString("pw"));
		MainApp.mainUser.setSchoolId(result.getString("schoolId"));
		MainApp.mainUser.setConfirm(result.getString("confirm"));
	}

	/**
	 * 
	 * 테스트값. 서버 구현되면 지울것 <br>
	   아직 모든 값들이 무엇을 의미하는지 알 수 없음.
	   
	 * @return
	 */
	private JSONObject mainUserTestValue() {
		JSONObject result = new JSONObject();
		try {
			result.put("acEmail", "chanroid@gmail.com");
			result.put("joinDate", "20140701");
			result.put("pictureNum", "0");
			result.put("mainPicture", "0");
			result.put("loginTypeId", "3");
			result.put("judge", "null");
			result.put("termsDate", "20140701");
			result.put("userId", "chanroid");
			result.put("userLoginId", "chanroid@gmail.com");
			result.put("pw", SHA256.getCipherText("doddodwmf"));
			result.put("schoolId", "kit");
			result.put("confirm", "1");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return result;
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
				if (isTypeEmail(v.getEditableText().toString())) {
					return false; // 액션 작동
				} else {
					layout.toast(R.string.error_email_confirm, Toast.LENGTH_SHORT);
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
				
				if (layout.inputPw.getText().toString().length() < 6) { // 비밀번호 자릿수
					layout.toast(R.string.error_password_confirm, Toast.LENGTH_SHORT);
					layout.inputPw.requestFocus();
				} else if (!isTypeEmail(layout.inputId.getText().toString())) { // 유효하지 않은 아이디
					layout.toast(R.string.error_email_confirm, Toast.LENGTH_SHORT);
					layout.inputId.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
					layout.inputId.requestFocus();
				} else {
					layout.inputId.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
					normalLoginAction();
				}
				break;
			case R.id.btn_normal_join:
				Intent joinIntent = new Intent(MainApp.appContext, kr.co.redstrap.campusting.join.JoinActivity.class);
				MainApp.mainUser.setLoginTypeId(CampusTingConstant.LoginType.CAMPUSTING);
				startActivity(joinIntent);
				finish();
				break;
			case R.id.btn_find_pw:
				break;
			case R.id.btn_facebook:
				Session.openActiveSession(UnitedLoginActivity.this, true, new Session.StatusCallback() {

					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session.isOpened()) {
							Request.newMeRequest(session, new GraphUserCallback() {

								@Override
								public void onCompleted(GraphUser user, Response response) {
									if (user != null) {
										facebookLoginAction(user, response);
									} else {
										layout.toast(R.string.error_connection, Toast.LENGTH_SHORT);
									}
								}
							}).executeAsync();
						} else {
							Log.i("세션 닫힘", "세션 닫힘");
						}
					}
				});
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

	/**
	 * 유저의 가입여부를 파악. 이미 가입된 유저라면 '1', 없으면 '0', 나머지는 에러코드임.
	 */
	private class IsUserCheckTask extends AsyncTask<String, Void, Character> {
		char checker = '!';

		@Override
		protected Character doInBackground(String... userInfo) {

			InputStream urlInputStream = null;
			try {
				URL url = new URL(UrlUtil.campusTingWebUrl + "isUserAlready?userLoginId=" + userInfo[0] + "&loginTypeId=" + userInfo[1]);
				Log.i("isUserAlready : ", userInfo[0] + " // " + userInfo[1]);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(2500);
				con.setUseCaches(false);
				urlInputStream = con.getInputStream();
				checker = (char) urlInputStream.read();
				con.disconnect();
				Log.i("체크", "" + checker);
			} catch (Exception e) {
				layout.toast(R.string.error_connection, Toast.LENGTH_SHORT);
				Log.e("에러", e.toString());
			} finally {
				if (urlInputStream != null) {
					try {
						urlInputStream.close();
					} catch (IOException e) {
						Log.e("IOException", e.toString()); // 스트림 닫기 에러
					}
				}
			}
			return checker;
		}
	}

	/**
	 * 이메일 '@', '.'의 위치를 통해 간략한 형식 체크
	 * 
	 * @param email
	 *            체크할 이메일
	 * @return 이메일 형식에 맞으면 true를 반환
	 */
	private boolean isTypeEmail(String email) {
		boolean atCheck = false;
		char[] emailChars = email.toCharArray();
		int emailLength = emailChars.length;
		int target = 0;
		for (target = 0; target < emailLength; target++) {
			if (emailChars[target] == '@') {
				atCheck = true;
				break;
			}
		}

		if (atCheck == false) {
			return false;
		}

		for (int i = target; i < emailLength; i++) {
			if (emailChars[i] == '.') {
				return true;
			}
		}
		return false;
	}
}
