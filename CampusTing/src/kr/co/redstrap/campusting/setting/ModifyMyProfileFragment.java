package kr.co.redstrap.campusting.setting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import kr.co.redstrap.campusting.join.JoinInfoSelectItems;
import kr.co.redstrap.campusting.join.frag.JoinInfoSelectPopupFragment;
import kr.co.redstrap.campusting.join.frag.JoinInfoSelectPopupFragment.Callback;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

public class ModifyMyProfileFragment extends Fragment implements
		ModifyMyProfileLayout.Callback, Callback {

	private ModifyMyProfileLayout layout;
	
	private boolean man;
	private int height;
	private boolean smoke;
	private String job;
	private String bloodType;
	private Integer body;
	private ArrayList<Integer> character;
	private Integer coupleCount;
	private Integer drink;
	private Integer local;
	private Integer major;
	private Integer religion;
	private String birth;
	
	private String univName;
	private String univMail;
	private boolean isUnivCard;
	private boolean isJobConfirmed;
	
	public void setConfirmedUniv(String name, String mail, boolean photo) {
		univName = name;
		univMail = mail;
		isUnivCard = photo;
		layout.setConfirmedUniv(univName, univMail, isUnivCard);
	}
	
	public void setConfirmedJob(boolean job) {
		isJobConfirmed = job;
		layout.setConfirmedJob(isJobConfirmed);
	}
	
	public boolean isMan() {
		return man;
	}

	public void setMan(boolean man) {
		this.man = man;
		onGenderChanged(man);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		layout.setHeight(String.valueOf(height));
	}

	public boolean isSmoke() {
		return smoke;
	}

	public void setSmoke(boolean smoke) {
		this.smoke = smoke;
		onSmokeChanged(smoke);
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
		layout.setJob(job);
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
		layout.setBlood(bloodType);
	}

	public Integer getBody() {
		return body;
	}

	public void setBody(Integer body) {
		this.body = body;
		layout.setBody(JoinInfoSelectItems.body[body]);
	}

	public ArrayList<Integer> getCharacter() {
		return character;
	}

	public void setCharacter(ArrayList<Integer> character) {
		this.character = character;
		layout.setCharacter(new String[]{
			JoinInfoSelectItems.characters[character.get(0)],
			JoinInfoSelectItems.characters[character.get(1)],
			JoinInfoSelectItems.characters[character.get(2)]
		});
	}

	public Integer getCoupleCount() {
		return coupleCount;
	}

	public void setCoupleCount(Integer coupleCount) {
		this.coupleCount = coupleCount;
		layout.setCoupleCount(JoinInfoSelectItems.couple[coupleCount]);
	}

	public Integer getDrink() {
		return drink;
	}

	public void setDrink(Integer drink) {
		this.drink = drink;
		layout.setDrink(JoinInfoSelectItems.drink[drink]);
	}

	public Integer getLocal() {
		return local;
	}

	public void setLocal(Integer local) {
		this.local = local;
		layout.setLocal(JoinInfoSelectItems.local[local]);
	}

	public Integer getMajor() {
		return major;
	}

	public void setMajor(Integer major) {
		this.major = major;
		layout.setMajor(JoinInfoSelectItems.major[major]);
	}

	public Integer getReligion() {
		return religion;
	}

	public void setReligion(Integer religion) {
		this.religion = religion;
		layout.setReligion(JoinInfoSelectItems.religion[religion]);
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
		layout.setBirth(birth);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new ModifyMyProfileLayout(getActivity());
		layout.setCallback(this);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		if (layout == null) {
			layout = new ModifyMyProfileLayout(getActivity());
			layout.setCallback(this);
		}

		return layout.getView();
	}

	private void showInfoSelectPopup(int requestCode) {
		JoinInfoSelectPopupFragment popup = new JoinInfoSelectPopupFragment(
				requestCode);
		popup.setCallback(this);
		popup.show(getActivity().getSupportFragmentManager(), null);
	}


	@Override
	public void onGenderChanged(boolean isman) {
		// TODO Auto-generated method stub
		man = isman;
	}

	@Override
	public void onBitrhClick() {
		// TODO Auto-generated method stub

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
				layout.setBirth(birth);
				ModifyMyProfileFragment.this.birth = birth;
			}
		};

		DatePickerDialog dialog = new DatePickerDialog(getActivity(),
				datecallback, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
		dialog.show();
	}

	@Override
	public void onLocalClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.LOCAL);
	}

	@Override
	public void onCharacterClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.CHARACTER);
	}

	@Override
	public void onBodyClick() {
		// TODO Auto-generated method stub
		if (man)
			showInfoSelectPopup(JoinInfoSelectItems.BODY_MALE);
		else
			showInfoSelectPopup(JoinInfoSelectItems.BODY_FEMALE);
	}

	@Override
	public void onHeightEdited(int height) {
		// TODO Auto-generated method stub
		this.height = height;
	}

	@Override
	public void onBloodClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.BLOOD_TYPE);
	}

	@Override
	public void onReligionClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.RELIGION);
	}

	@Override
	public void onSmokeChanged(boolean smoke) {
		// TODO Auto-generated method stub
		this.smoke = smoke;
	}

	@Override
	public void onDrinkClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.DRINK);
	}

	@Override
	public void onJobEdit(String job) {
		// TODO Auto-generated method stub
		this.job = job;
	}

	public int getUnivNum() {
		// TODO Auto-generated method stub
		// 20140812 chanroid db찾으면 구현
		return 0;
	}

	@Override
	public void onMajorClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.MAJOR);
	}

	@Override
	public void onCouplecountClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.COUPLECOUNT);
	}

	@Override
	public void onConfirmunivClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConfirmjobClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConfirmed(int requestCode,
			ArrayList<Map<String, Object>> confirmedItems) {
		// TODO Auto-generated method stub

		switch (requestCode) {
		case JoinInfoSelectItems.BLOOD_TYPE:
			bloodType = (String) confirmedItems.get(0).get("title");
			layout.setBlood(bloodType);
			break;
		case JoinInfoSelectItems.BODY_FEMALE:
		case JoinInfoSelectItems.BODY_MALE:
			body = (Integer) confirmedItems.get(0).get("index");
			layout.setBody((String) confirmedItems.get(0).get("title"));
			break;
		case JoinInfoSelectItems.CHARACTER:
			ArrayList<Integer> characters = new ArrayList<Integer>();
			ArrayList<String> characterText = new ArrayList<String>();
			for (Map<String, Object> map : confirmedItems) {
				characters.add((Integer) map.get("index"));
				characterText.add((String) map.get("title"));
			}

			character = characters;
			layout.setCharacter(new String[] { characterText.get(0),
					characterText.get(1), characterText.get(2) });

			break;
		case JoinInfoSelectItems.COUPLECOUNT:
			coupleCount = (Integer) confirmedItems.get(0).get("index");
			layout.setCoupleCount((String) confirmedItems.get(0).get("title"));
			break;
		case JoinInfoSelectItems.DRINK:
			drink = (Integer) confirmedItems.get(0).get("index");
			layout.setDrink((String) confirmedItems.get(0).get("title"));
			break;
		case JoinInfoSelectItems.LOCAL:
			local = (Integer) confirmedItems.get(0).get("index");
			layout.setLocal((String) confirmedItems.get(0).get("title"));
			break;
		case JoinInfoSelectItems.MAJOR:
			major = (Integer) confirmedItems.get(0).get("index");
			layout.setMajor((String) confirmedItems.get(0).get("title"));
			break;
		case JoinInfoSelectItems.RELIGION:
			religion = (Integer) confirmedItems.get(0).get("index");
			layout.setReligion((String) confirmedItems.get(0).get("title"));
			break;
		}
	}

}
