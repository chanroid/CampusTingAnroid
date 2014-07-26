package kr.co.redstrap.campusting.login;

import kr.co.redstrap.campusting.MainApp;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;

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
	
	private View introTitle;
	private View txtSummary;
//	private View loginInterface;
	private View loginBtnInterface;
	private View fbBtn;
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
		introTitle = findViewById(R.id.layout_intro_title);
		txtSummary = findViewById(R.id.txt_summary);
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

		// 1. 페이스북 버튼 뷰 연결
		fbBtn = findViewById(R.id.btn_facebook);
		// 2. 네이버 버튼 뷰 연결
		// 3. 일반 로그인 버튼 뷰
		normalLoginBtn = findViewById(R.id.btn_normal_login);

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
	}

	/**
	 * 로그인 관련 뷰들을 표시합니다
	 */
	public void showLoginViews() {
		// 0. 타이틀 인
		Animation anim = AnimationUtils.loadAnimation(MainApp.appContext, R.anim.trans_up); 
		// 인트로 타이틀 통째로 y축으로 -20% 이동하는 애니메이션
		anim.setStartOffset(1000); // 1초 후에 실행
		introTitle.startAnimation(anim);
		AnimUtil.startFadeOutAnim(txtSummary, 1000, 1000);
		// 타이틀 하단 부제 페이드 아웃

		// 1. 로그인 버튼 인터페이스 생성
		fbBtn.setClickable(true);
		normalLoginBtn.setClickable(true);
		AnimUtil.startFadeInAnim(loginBtnInterface, 1000, 1000);
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
