package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.common.SimpleTextWatcher;
import kr.co.redstrap.campusting.util.ViewUtil;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NormalJoinLayout extends AbsCTLayout {
	
	public interface Callback {
		// 패스워드는 클래스 안에서 바로 처리
		public void onNextClick();
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	private EditText emailIdEdit;
	private EditText pwEdit;
	private EditText pwConfirmEdit;
	private EditText nickEdit;
	private RadioGroup genderRadioGroup;
	private Button birthBtn;
	private EditText promoCodeEdit;
	private Button nextBtn;
	
	private String gender = "M";

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
		
		Listener l = new Listener();

		// 뷰 연결
		emailIdEdit = (EditText) findViewById(R.id.emailIdEdit);
		emailIdEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (ViewUtil.isTypeEmail(s.toString())) {
					// 20140805 chanroid 서버에 중복확인 요청까지 같이 하도록 처리해야 함
					ViewUtil.setGood(emailIdEdit);
				} else {
					ViewUtil.setBad(emailIdEdit);
				}
			}
		});
		
		pwEdit = (EditText) findViewById(R.id.pwEdit);
		pwEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() < 6)
					ViewUtil.setBad(pwEdit);
				else
					ViewUtil.setGood(pwEdit);
			}
		});
		pwConfirmEdit = (EditText) findViewById(R.id.pwConfirmEdit);
		pwConfirmEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.toString().equals(s.toString()))
					ViewUtil.setGood(pwConfirmEdit);
				else
					ViewUtil.setBad(pwConfirmEdit);
			}
		});
		nickEdit = (EditText) findViewById(R.id.nickEdit);
		nickEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() > 1) {
					// 20140805 chanroid 서버에 중복확인 요청까지 같이 하도록 처리해야 함
					ViewUtil.setGood(nickEdit);
				} else {
					ViewUtil.setBad(nickEdit);
				}
			}
		});
		genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
		genderRadioGroup.setOnCheckedChangeListener(l);

		birthBtn = (Button) findViewById(R.id.birthBtn);
		birthBtn.setOnClickListener(l);

		promoCodeEdit = (EditText) findViewById(R.id.promoCodeEdit);
		promoCodeEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() == 6) { // 일단 6자리로 세팅놓고 변경되면 확인
					// 20140805 chanroid 서버에 유효값 확인 요청까지 같이 하도록 처리해야 함
					ViewUtil.setGood(promoCodeEdit);
				} else {
					ViewUtil.setBad(promoCodeEdit);
				}
			}
		});
		nextBtn = (Button) findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(l);

	}
	
	public String getEmailId() {
		return emailIdEdit.getText().toString();
	}
	
	public void setEmailId(String email) {
		emailIdEdit.setText(email);
		emailIdEdit.setEnabled(false);
	}
	
	public String getPwText() {
		return pwEdit.getText().toString();
	}

	public String getPwConfirmText() {
		return pwConfirmEdit.getText().toString();
	}
	
	public void setPwText(String pw) {
		pwEdit.setText(pw);
		pwConfirmEdit.setText(pw);
		
		pwEdit.setEnabled(false);
		pwConfirmEdit.setEnabled(false);
	}
	
	public String getNickname() {
		return nickEdit.getText().toString();
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		if ("M".equals(gender))
			genderRadioGroup.check(R.id.radio_mail);
		else if ("F".equals(gender))
			genderRadioGroup.check(R.id.radio_femail);
		
		genderRadioGroup.setEnabled(false);
	}
	
	public String getPromoCode() {
		return promoCodeEdit.getText().toString();
	}
	
	// 20140805 chanroid 임의값으로 하고 나중에 팝업 구현되면 해당 값 넣을것 
	public String getBirth() {
		return "198880811";
	}
	
	public void setBirth(String birth) {
		birthBtn.setText(birth);
		birthBtn.setEnabled(false);
	}
	
	public boolean isConfirmed() {
		// 20140805 chanroid 일단 테스트로 이렇게 만들어 놓고 얘네들 따로 변수로 관리해야 함. 서버모듈 붙이고.. 팝업 달고...
		boolean confirmEmail = ViewUtil.isTypeEmail(getEmailId());
		boolean confirmPw = getPwText().equals(getPwConfirmText()) && getPwText().length() > 5;
		boolean confirmNick = getNickname().length() > 1;
		boolean confirmBirth = !getBirth().equals("00000000");
		return confirmEmail && confirmPw && confirmNick && confirmBirth;
	}

	private class Listener implements OnClickListener, OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.radio_mail:
				gender = "M";
				break;
			case R.id.radio_femail:
				gender = "F";
				break;
			}
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.birthBtn:
				// 생일 설정하는 팝업창 띄움
				break;
			case R.id.nextBtn:
				callback.onNextClick();
				break;
			}
		}

	}
}
