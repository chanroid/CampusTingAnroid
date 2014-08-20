package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.common.SimpleTextWatcher;
import kr.co.redstrap.campusting.util.ViewUtil;
import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ModifyMyProfileLayout extends AbsCTLayout {

	public interface Callback {
		public void onBitrhClick();

		public void onLocalClick();

		public void onCharacterClick();

		public void onBodyClick();

		public void onHeightEdited(int height);

		public void onBloodClick();

		public void onReligionClick();

		public void onSmokeChanged(boolean smoke);

		public void onDrinkClick();

		public void onJobEdit(String job);

		public void onMajorClick();

		public void onCouplecountClick();

		public void onConfirmunivClick();

		public void onConfirmjobClick();
	}

	private Callback callback;

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public void setNickName(String nick) {
		nicknameText.setText(nick);
	}

	public void setGender(boolean isman) {
		genderText.setText(isman ? "남자" : "여자");
	}

	public void setBirth(String birth) {
		birthBtn.setText(birth);
	}

	public void setLocal(String local) {
		localBtn.setText(local);
	}

	public void setCharacter(String[] strings) {
		characterBtn
				.setText(strings[0] + ", " + strings[1] + ", " + strings[2]);
	}

	public void setBody(String body) {
		bodyBtn.setText(body);
	}

	public void setHeight(String height) {
		heightEdit.setText(height);
	}

	public void setBlood(String blood) {
		bloodBtn.setText(blood);
	}

	public void setReligion(String religion) {
		religionBtn.setText(religion);
	}

	public void setSmoke(boolean issmoke) {
		smokeRadio.check(issmoke ? R.id.modifyintroduceSmokeRadio1
				: R.id.modifyintroduceSmokeRadio2);
	}

	public void setDrink(String drink) {
		drinkBtn.setText(drink);
	}

	public void setJob(String job) {
		jobEdit.setText(job);
	}

	public void setMajor(String major) {
		majorBtn.setText(major);
	}

	public void setCoupleCount(String count) {
		couplecountBtn.setText(count);
	}

	public void setConfirmedUniv(String name, String mail, boolean photo) {
		// 20140820 chanroid 테스트시 일단 표시는 하도록
//		if (name != null) {
//			confirmunivLayout.setVisibility(View.GONE);
//			confirmedunivLayout.setVisibility(View.VISIBLE);
//			confirmedunivNameText.setText(name);
//			confirmedunivMailText.setText(mail);
//		} else {
//			confirmunivLayout.setVisibility(View.VISIBLE);
//			confirmedunivLayout.setVisibility(View.GONE);
//		}
	}

	public void setConfirmedJob(boolean confirmed) {
		// 20140820 chanroid 테스트시 일단 표시는 하도록
//		confirmJobLayout.setVisibility(confirmed ? View.GONE : View.VISIBLE);
//		confirmedJobLayout.setVisibility(confirmed ? View.VISIBLE : View.GONE);
	}

	public TextView nicknameText;
	public TextView genderText;
	public Button birthBtn;
	public Button localBtn;
	public Button characterBtn;
	public Button bodyBtn;
	public EditText heightEdit;
	public Button bloodBtn;
	public Button religionBtn;
	public RadioGroup smokeRadio;
	public Button drinkBtn;
	public EditText jobEdit;
	public Button majorBtn;
	public Button couplecountBtn;

	public LinearLayout confirmunivLayout;
	public Button confirmunivBtn;

	public LinearLayout confirmedunivLayout;
	public TextView confirmedunivNameText;
	public TextView confirmedunivMailText;

	public LinearLayout confirmJobLayout;
	public Button confirmJobBtn;

	public LinearLayout confirmedJobLayout;

	public ModifyMyProfileLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_modify_profile_introduce;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		Listener l = new Listener();

		nicknameText = (TextView) findViewById(R.id.modifyintroduceNicknameText);
		genderText = (TextView) findViewById(R.id.modifyintroduceGenderText);
		birthBtn = (Button) findViewById(R.id.modifyintroduceBirthBtn);
		birthBtn.setOnClickListener(l);
		localBtn = (Button) findViewById(R.id.modifyintroduceLocalBtn);
		localBtn.setOnClickListener(l);
		characterBtn = (Button) findViewById(R.id.modifyintroduceCharacterBtn);
		characterBtn.setOnClickListener(l);
		bodyBtn = (Button) findViewById(R.id.modifyintroduceBodyBtn);
		bodyBtn.setOnClickListener(l);
		heightEdit = (EditText) findViewById(R.id.modifyintroduceHeightBtn);
		heightEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if ("".equals(s.toString()))
					return;
				
				int height = Integer.parseInt(s.toString());
				if (height > 100 && height < 300) {
					ViewUtil.setGood(heightEdit);
					callback.onHeightEdited(height);
				} else {
					ViewUtil.setBad(heightEdit);
				}
			}
		});
		bloodBtn = (Button) findViewById(R.id.modifyintroduceBloodBtn);
		bloodBtn.setOnClickListener(l);
		religionBtn = (Button) findViewById(R.id.modifyintroduceReligionBtn);
		religionBtn.setOnClickListener(l);
		smokeRadio = (RadioGroup) findViewById(R.id.modifyintroduceSmokeRadioGroup);
		smokeRadio.setOnCheckedChangeListener(l);
		drinkBtn = (Button) findViewById(R.id.modifyintroduceDrinkBtn);
		drinkBtn.setOnClickListener(l);
		jobEdit = (EditText) findViewById(R.id.modifyintroduceJobEdit);
		jobEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() < 2) {
					ViewUtil.setBad(jobEdit);
				} else {
					ViewUtil.setGood(jobEdit);
					callback.onJobEdit(s.toString());
				}
			}
		});
		majorBtn = (Button) findViewById(R.id.modifyintroduceMajorBtn);
		majorBtn.setOnClickListener(l);
		couplecountBtn = (Button) findViewById(R.id.modifyintroduceCouplecountBtn);
		couplecountBtn.setOnClickListener(l);

		confirmunivLayout = (LinearLayout) findViewById(R.id.modifyintroduceConfirmUnivLayout);
		confirmunivBtn = (Button) findViewById(R.id.modifyintroduceConfirmUnivBtn);
		confirmunivBtn.setOnClickListener(l);

		confirmedunivLayout = (LinearLayout) findViewById(R.id.modifyintroduceConfirmedUnivLayout);
		confirmedunivNameText = (TextView) findViewById(R.id.modifyintroduceConfirmedUnivText);
		confirmedunivMailText = (TextView) findViewById(R.id.modifyintroduceConfirmedUnivMailText);

		confirmJobLayout = (LinearLayout) findViewById(R.id.modifyintroduceConfirmJobLayout);
		confirmJobBtn = (Button) findViewById(R.id.modifyintroduceConfirmJobBtn);
		confirmJobBtn.setOnClickListener(l);

		confirmedJobLayout = (LinearLayout) findViewById(R.id.modifyintroduceConfirmedJobLayout);

	}

	private class Listener implements OnClickListener, OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (group.getId()) {
			case R.id.modifyintroduceSmokeRadioGroup:
				callback.onSmokeChanged(checkedId == R.id.modifyintroduceSmokeRadio1);
			}
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.modifyintroduceBirthBtn:
				callback.onBitrhClick();
				break;
			case R.id.modifyintroduceLocalBtn:
				callback.onLocalClick();
				break;
			case R.id.modifyintroduceCharacterBtn:
				callback.onCharacterClick();
				break;
			case R.id.modifyintroduceBodyBtn:
				callback.onBodyClick();
				break;
			case R.id.modifyintroduceBloodBtn:
				callback.onBloodClick();
				break;
			case R.id.modifyintroduceReligionBtn:
				callback.onReligionClick();
				break;
			case R.id.modifyintroduceDrinkBtn:
				callback.onDrinkClick();
				break;
			case R.id.modifyintroduceMajorBtn:
				callback.onMajorClick();
				break;
			case R.id.modifyintroduceCouplecountBtn:
				callback.onCouplecountClick();
				break;
			case R.id.modifyintroduceConfirmUnivBtn:
				callback.onConfirmunivClick();
				break;
			case R.id.modifyintroduceConfirmJobBtn:
				callback.onConfirmjobClick();
				break;
			}
		}

	}

}
