package kr.co.redstrap.campusting.join.frag;

import java.util.Locale;

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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class InfoLayout extends AbsCTLayout {

	public interface Callback {
		public void onLocalClick();

		public void onCharacterClick();

		public void onBodyClick();

		public void onBloodTypeClick();

		public void onReligionClick();

		public void onSmokeChange(boolean smoke);

		public void onDrinkClick();

		public void onHeightEdited(int height);

		public void onJobEdited(String job);

		public void onMajorClick();

		public void onCoupleCountClick();

		public void onConfirmClick();
	}

	private Callback callback;

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	private Button localBtn;
	private Button characterBtn;
	private Button bodyBtn;
	private EditText heightEdit;
	private Button bloodTypeBtn;
	private Button religionBtn;
	private RadioGroup smokeRadioGroup;
	private Button drinkBtn;
	private EditText jobEdit;
	private Button majorBtn;
	private Button coupleCountBtn;
	private Button confirmBtn;

	public void setLocal(String local) {
		localBtn.setText(local);
	}

	public void setCharacter(String[] characters) {
		String format = "%s, %s, %s";
		characterBtn.setText(String.format(Locale.getDefault(), format, characters));
	}

	public void setBody(String body) {
		bodyBtn.setText(body);
	}

	public void setBloodType(String type) {
		bloodTypeBtn.setText(type);
	}

	public void setReligion(String religion) {
		religionBtn.setText(religion);
	}

	public void setDrink(String drink) {
		drinkBtn.setText(drink);
	}

	public void setMajor(String major) {
		majorBtn.setText(major);
	}

	public void setCoupleCount(String couplecount) {
		coupleCountBtn.setText(couplecount);
	}

	public InfoLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_join_info;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		Listener l = new Listener();

		localBtn = (Button) findViewById(R.id.joininfoLocalBtn);
		localBtn.setOnClickListener(l);
		characterBtn = (Button) findViewById(R.id.joininfoCharacterBtn);
		characterBtn.setOnClickListener(l);
		bodyBtn = (Button) findViewById(R.id.joininfoBodyBtn);
		bodyBtn.setOnClickListener(l);
		heightEdit = (EditText) findViewById(R.id.joininfoHeightBtn);
		heightEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				try {
					if (Integer.parseInt(s.toString()) < 100
							|| Integer.parseInt(s.toString()) > 300) {
						ViewUtil.setBad(heightEdit);
					} else {
						ViewUtil.setGood(heightEdit);
						callback.onHeightEdited(Integer.parseInt(s.toString()));
					}
				} catch (NumberFormatException e) {
					ViewUtil.setBad(heightEdit);
				}
			}
		});
		bloodTypeBtn = (Button) findViewById(R.id.joininfoBloodBtn);
		bloodTypeBtn.setOnClickListener(l);
		religionBtn = (Button) findViewById(R.id.joininfoReligionBtn);
		religionBtn.setOnClickListener(l);
		smokeRadioGroup = (RadioGroup) findViewById(R.id.joininfoSmokeRadio);
		smokeRadioGroup.setOnCheckedChangeListener(l);
		drinkBtn = (Button) findViewById(R.id.joininfoDrinkBtn);
		drinkBtn.setOnClickListener(l);
		jobEdit = (EditText) findViewById(R.id.joininfoJobEdit);
		jobEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onJobEdited(s.toString());
			}
		});
		majorBtn = (Button) findViewById(R.id.joininfoMajorBtn);
		majorBtn.setOnClickListener(l);
		coupleCountBtn = (Button) findViewById(R.id.joininfoCoupleCountBtn);
		coupleCountBtn.setOnClickListener(l);
		confirmBtn = (Button) findViewById(R.id.joininfoConfirmBtn);
		confirmBtn.setOnClickListener(l);

	}

	private class Listener implements OnClickListener, OnCheckedChangeListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.joininfoLocalBtn:
				callback.onLocalClick();
				break;
			case R.id.joininfoCharacterBtn:
				callback.onCharacterClick();
				break;
			case R.id.joininfoBodyBtn:
				callback.onBodyClick();
				break;
			case R.id.joininfoBloodBtn:
				callback.onBloodTypeClick();
				break;
			case R.id.joininfoReligionBtn:
				callback.onReligionClick();
				break;
			case R.id.joininfoDrinkBtn:
				callback.onDrinkClick();
				break;
			case R.id.joininfoMajorBtn:
				callback.onMajorClick();
				break;
			case R.id.joininfoCoupleCountBtn:
				callback.onCoupleCountClick();
				break;
			case R.id.joininfoConfirmBtn:
				callback.onConfirmClick();
				break;
			}
		}

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.joininfoSmokeRadio1: // 흡연
				callback.onSmokeChange(true);
				break;
			case R.id.joininfoSmokeRadio2: // 비흡연
				callback.onSmokeChange(false);
				break;
			}
		}

	}

}
