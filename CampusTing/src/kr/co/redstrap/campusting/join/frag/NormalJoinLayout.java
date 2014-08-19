package kr.co.redstrap.campusting.join.frag;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.SimpleTextWatcher;
import kr.co.redstrap.campusting.util.ViewUtil;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

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

	private boolean gender = true;
	private String birth = "";

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
//					ViewUtil.setGood(emailIdEdit);
					checkDuplicate(0, s.toString(), emailIdEdit);
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
//					ViewUtil.setGood(nickEdit);
					checkDuplicate(1, s.toString(), nickEdit);
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
//					ViewUtil.setGood(promoCodeEdit);
					checkDuplicate(2, s.toString(), promoCodeEdit);
				} else {
					ViewUtil.setBad(promoCodeEdit);
				}
			}
		});
		nextBtn = (Button) findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(l);

	}
	
	private void checkDuplicate(int code, String value, final TextView resultCheck) {
		CTJSONSyncTask task = new CTJSONSyncTask();
		task.addHttpParam("acceptType", code);
		task.addHttpParam("acceptValue", value);
		task.addCallback(new CTSyncTaskCallback.Stub() {
			@Override
			public void onErrorTask(AbsCTSyncTask<String, Object> task,
					ErrorResult error) {
				// TODO Auto-generated method stub
				ViewUtil.setBad(resultCheck);
			}
			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object result) {
				// TODO Auto-generated method stub
				JSONObject resultJSON = (JSONObject) result;
				
				try {
					int duplicated = resultJSON.getInt("count");
					if (duplicated > 0)
						ViewUtil.setBad(resultCheck);
					else
						ViewUtil.setGood(resultCheck);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		task.execute("userCheck");
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

	public boolean getGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		if (gender)
			genderRadioGroup.check(R.id.radio_mail);
		else
			genderRadioGroup.check(R.id.radio_femail);

		genderRadioGroup.setEnabled(false);
	}

	public String getPromoCode() {
		return promoCodeEdit.getText().toString();
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		if (birth != null) {
			birthBtn.setText(birth);
			birthBtn.setEnabled(false);
		}
	}

	public boolean isConfirmed() {
		boolean confirmEmail = ViewUtil.isTypeEmail(getEmailId());
		boolean confirmPw = getPwText().equals(getPwConfirmText())
				&& getPwText().length() > 5;
		boolean confirmNick = getNickname().length() > 1;
		boolean confirmBirth = !"".equals(getBirth());
		return confirmEmail && confirmPw && confirmNick && confirmBirth;
	}

	private class Listener implements OnClickListener, OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.radio_mail:
				gender = true;
				break;
			case R.id.radio_femail:
				gender = false;
				break;
			}
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.birthBtn:
				// 생일 설정하는 팝업창 띄움
				GregorianCalendar calendar = new GregorianCalendar(
						Locale.getDefault());

				DatePickerDialog.OnDateSetListener datecallback = new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						String format = "%04d%02d%02d";
						String birth = String.format(format, year,
								monthOfYear + 1, dayOfMonth);
						birthBtn.setText(birth);
						NormalJoinLayout.this.birth = birth;
					}
				};

				DatePickerDialog dialog = new DatePickerDialog(getContext(),
						datecallback, calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH) + 1,
						calendar.get(Calendar.DAY_OF_MONTH));
				dialog.show();
				break;
			case R.id.nextBtn:
				callback.onNextClick();
				break;
			}
		}

	}
}
