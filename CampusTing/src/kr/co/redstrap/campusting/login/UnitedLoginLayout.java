package kr.co.redstrap.campusting.login;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.util.AnimUtil;
import kr.co.redstrap.campusting.util.indicator.TabPageIndicator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView.OnEditorActionListener;

import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

/**
 * 로그인 레이아웃
 * 
 * @author rbi_bi_3
 *
 */
public class UnitedLoginLayout extends AbsCTLayout {

	boolean isSwaped = false;
	
	public TabPageIndicator indicator;

	public EditText inputId;
	public EditText inputPw;
	
	private ImageView splash1;
	private ImageView splash2;
	
//	private View loginInterface;
	private View loginBtnInterface;
	private View fbBtn;
	private OAuthLoginButton naverBtn;
	private View normalLoginBtn;
	private View loginNormalInterface;
	private View loginCancel;
	private View loginConfirm;
	private View normalJoin;
	private View findPw;

	// 아이디 유효성 표시 아이콘
	private Drawable iconBad;
	private Drawable iconGood;
	
	public UnitedLoginLayout(Context ctx) {
		super(ctx);
		
		iconGood = ctx.getResources().getDrawable(R.drawable.icon_good);
		iconBad = ctx.getResources().getDrawable(R.drawable.icon_bad);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_login;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		// 뷰 연결
		// 타이틀 및 컨테이너(인터페이스)
//		loginInterface = findViewById(R.id.login_interface);
		loginBtnInterface = findViewById(R.id.login_btn_interface);
		loginNormalInterface = findViewById(R.id.login_normal_interface);
		// 0. 일반 로그인 입력창 셋팅
		inputId = (EditText) findViewById(R.id.input_id);
		inputPw = (EditText) findViewById(R.id.input_pw);
		loginCancel = findViewById(R.id.btn_normal_login_cancel);
		loginConfirm = findViewById(R.id.btn_normal_login_confirm);
		normalJoin = findViewById(R.id.btn_normal_join);
		findPw = findViewById(R.id.btn_find_pw);
		
		splash1 = (ImageView) findViewById(R.id.introLoading1Img);
		splash2 = (ImageView) findViewById(R.id.introLoading2Img);

		// 1. 페이스북 버튼 뷰 연결
		fbBtn = findViewById(R.id.btn_facebook);
		// 2. 네이버 버튼 뷰 연결
		naverBtn = (OAuthLoginButton) findViewById(R.id.btn_naver);
		naverBtn.setBgType("green", "long");
		// 3. 일반 로그인 버튼 뷰
		normalLoginBtn = findViewById(R.id.btn_normal_login);

	}
	
	public void setNaverloginHandler(OAuthLoginHandler handler) {
		naverBtn.setOAuthLoginHandler(handler);
	}
	
	public void performConfirm() {
		loginConfirm.performClick();
	}
	
	public void changeInputIdIcon(boolean isGood) {
		inputId.setCompoundDrawablesWithIntrinsicBounds(null, null, isGood ? iconGood : iconBad, null);
	}
	
	public void setPwInputFilters(InputFilter[] filter) {
		inputPw.setFilters(filter);
	}
	
	public void addIdTextChangedListener(TextWatcher listener) {
		inputId.addTextChangedListener(listener);
	}
	
	public void setEditorListener(OnEditorActionListener listener) {

		// 에디터 액션 리스너
		inputId.setOnEditorActionListener(listener);
		inputPw.setOnEditorActionListener(listener);
	}
	
	public void setClickListener(OnClickListener listener) {

		// 뷰 셋팅
		// 클릭 리스너
		normalLoginBtn.setOnClickListener(listener);
		loginCancel.setOnClickListener(listener);
		loginConfirm.setOnClickListener(listener);
		normalJoin.setOnClickListener(listener);
		findPw.setOnClickListener(listener);
		fbBtn.setOnClickListener(listener);
		naverBtn.setOnClickListener(listener);
	}

	/**
	 * 로그인 관련 뷰들을 표시합니다
	 */
	public void showLoginViews() {

		// 1. 로그인 버튼 인터페이스 생성
		fbBtn.setClickable(true);
		naverBtn.setClickable(true);
		normalLoginBtn.setClickable(true);
		AnimUtil.startFadeInAnim(loginBtnInterface, 1000, 1000);
		
		AnimUtil.startFadeInAnim(splash2, 1000, 1000);
		AnimUtil.startFadeOutAnim(splash1, 1000, 1000);
	}
	
	/**
	 * 로그인 인터페이스를 전환합니다
	 */
	public void swapLoginInterface() {
		if (isSwaped) {
			isSwaped = false;
			// 1. 버튼 인터페이스 인
			final Handler handler = new Handler();
			new Thread(new Runnable() {

				@Override
				public void run() {
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							fbBtn.setClickable(true);
							naverBtn.setClickable(true);
							normalLoginBtn.setClickable(true);
							inputId.setText("");
							inputPw.setText("");
						}

					}, AnimUtil.DEFAULT_ANIM_DURATION + 100);
				}
			}).start();

			AnimUtil.startFadeInAnim(loginBtnInterface);
			// 2. 자체 로그인 인터페이스 아웃
			inputId.setClickable(false);
			inputPw.setClickable(false);
			loginCancel.setClickable(false);
			loginConfirm.setClickable(false);
			normalJoin.setClickable(false);
			findPw.setClickable(false);
			AnimUtil.startFadeOutAnim(loginNormalInterface);
		} else {
			isSwaped = true;
			// 1. 버튼 인터페이스 아웃
			fbBtn.setClickable(false);
			naverBtn.setClickable(false);
			normalLoginBtn.setClickable(false);
			AnimUtil.startFadeOutAnim(loginBtnInterface);
			// 2. 자체 로그인 인터페이스 인
			final Handler handler = new Handler();
			new Thread(new Runnable() {

				@Override
				public void run() {
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							inputId.setClickable(true);
							inputPw.setClickable(true);
							loginCancel.setClickable(true);
							loginConfirm.setClickable(true);
							normalJoin.setClickable(true);
							findPw.setClickable(true);
						}
					}, AnimUtil.DEFAULT_ANIM_DURATION + 100);
				}
			}).start();

			AnimUtil.startFadeInAnim(loginNormalInterface);
		}

	}

}
