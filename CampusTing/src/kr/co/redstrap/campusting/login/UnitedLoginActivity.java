package kr.co.redstrap.campusting.login;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.constant.CampusTingConstant.LoginType;
import kr.co.redstrap.campusting.main.MainActivity;
import kr.co.redstrap.campusting.util.PwInputFilter;
import kr.co.redstrap.campusting.util.SHA256;
import kr.co.redstrap.campusting.util.ViewUtil;
import kr.co.redstrap.campusting.util.indicator.TabPageIndicator;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
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
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				layout.changeInputIdIcon(ViewUtil.isTypeEmail(s.toString()));
			}
		});

		setContentView(layout.getView());

		// 인트로 시작
		introStart();

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
			if (requestCode == 64206) { // 페이스북
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
	private void facebookLoginAction(GraphUser user, Response response) {
		// 20140804 chanroid 페이스북 로그인은 나중에...
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
			case R.id.btn_facebook:
				Session.openActiveSession(UnitedLoginActivity.this, true,
						new Session.StatusCallback() {

							@Override
							public void call(Session session,
									SessionState state, Exception exception) {
								if (session.isOpened()) {
									Request.newMeRequest(session,
											new GraphUserCallback() {

												@Override
												public void onCompleted(
														GraphUser user,
														Response response) {
													if (user != null) {
														facebookLoginAction(
																user, response);
													} else {
														layout.toast(
																R.string.error_connection,
																Toast.LENGTH_SHORT);
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

	@Override
	public void onStartTask(AbsCTSyncTask<String, Object> task) {
		// TODO Auto-generated method stub
		layout.showLoading("Loading...");
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
