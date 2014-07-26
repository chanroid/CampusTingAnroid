package kr.co.redstrap.campusting.join.frag;

import java.util.Date;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RadioGroup;

public class NormalJoinLayout extends AbsCTLayout {
	
	public interface Callback {
		public void onEmailChanged(String email);
		// 패스워드는 클래스 안에서 바로 처리
		public void onNicknameChanged(String nickname);
		public void onGenderChanged(boolean isMan);
		public void onBirthChanged(Date date);
		public void onPromocodeChanged(String code);
		public void onNextClick();
	}

	private EditText emailIdEdit;
	private CheckedTextView emailIdCheck;

	private EditText pwEdit;
	private CheckedTextView pwEditCheck;

	private EditText pwConfirmEdit;
	private CheckedTextView pwConfirmEditCheck;

	private EditText nickEdit;
	private CheckedTextView nickEditCheck;

	private RadioGroup genderRadioGroup;

	private Button birthBtn;

	private EditText promoCodeEdit;
	private CheckedTextView promoCodeCheck;

	private Button nextBtn;

	public NormalJoinLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_join_normal_join;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		// 뷰 연결
		emailIdEdit = (EditText) findViewById(R.id.emailIdEdit);
		emailIdCheck = (CheckedTextView) findViewById(R.id.emailIdCheck);

		pwEdit = (EditText) findViewById(R.id.pwEdit);
		pwEditCheck = (CheckedTextView) findViewById(R.id.pwEditCheck);

		pwConfirmEdit = (EditText) findViewById(R.id.pwConfirmEdit);
		pwConfirmEditCheck = (CheckedTextView) findViewById(R.id.pwConfirmEditCheck);

		nickEdit = (EditText) findViewById(R.id.nickEdit);
		nickEditCheck = (CheckedTextView) findViewById(R.id.nickEditCheck);

		genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);

		birthBtn = (Button) findViewById(R.id.birthBtn);

		promoCodeEdit = (EditText) findViewById(R.id.promoCodeEdit);
		promoCodeCheck = (CheckedTextView) findViewById(R.id.promoCodeCheck);

		nextBtn = (Button) findViewById(R.id.nextBtn);

	}

}
