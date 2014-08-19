package kr.co.redstrap.campusting.join.frag;

import java.util.ArrayList;
import java.util.Map;

import kr.co.redstrap.campusting.join.AbsJoinFrag;
import kr.co.redstrap.campusting.join.JoinInfoSelectItems;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InfoFrag extends AbsJoinFrag implements InfoLayout.Callback,
		JoinInfoSelectPopupFragment.Callback {

	private InfoLayout layout;

	public int local = -1;
	public ArrayList<Integer> character = new ArrayList<Integer>();
	public String characterConstText;
	public int body = -1;
	public int height = -1;
	public String bloodType = "C";
	public int religion = -1;
	public boolean smoke = true;
	public int drink = -1;
	public String job = "";
	public int major = -1;
	public int coupleCount = -1;

	public InfoFrag() {
		super();
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("InfoFrag :: onResume", "onResume");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		layout = new InfoLayout(getActivity());
		layout.setCallback(this);

		return layout.getView();
	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
		boolean confirmLocal = local != -1;
		boolean confirmCharacter = character.size() > 0;
		boolean confirmBody = body != -1;
		boolean confirmHeight = height > 100 && height < 300;
		boolean confirmBlood = !"C".equals(bloodType);
		boolean confirmReligion = religion != -1;
		boolean confirmDrink = drink != -1;
		boolean confirmMajor = major != -1;
		boolean confirmCoupleCount = coupleCount != -1;

		Log.d("InfoFrag", "isConfirmed : " + confirmBlood + "," + confirmBody
				+ "," + confirmCharacter + "," + confirmCoupleCount + ","
				+ confirmDrink + "," + confirmHeight + "," + confirmLocal + ","
				+ confirmMajor + "," + confirmReligion);

		return confirmBlood & confirmBody & confirmCharacter
				& confirmCoupleCount & confirmDrink & confirmHeight
				& confirmLocal & confirmMajor & confirmReligion;
	}

	private void showInfoSelectPopup(int requestCode) {
		JoinInfoSelectPopupFragment popup = new JoinInfoSelectPopupFragment(
				requestCode);
		popup.setCallback(this);
		popup.show(getActivity().getSupportFragmentManager(), null);
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
		if (actContext.normalFrag.getInfo().gender)
			showInfoSelectPopup(JoinInfoSelectItems.BODY_MALE);
		else
			showInfoSelectPopup(JoinInfoSelectItems.BODY_FEMALE);
		// 20140807 chanroid 기본정보에서 입력된 성별로 구분해야 함
	}

	@Override
	public void onBloodTypeClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.BLOOD_TYPE);
	}

	@Override
	public void onReligionClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.RELIGION);
	}

	@Override
	public void onSmokeChange(boolean smoke) {
		// TODO Auto-generated method stub
		this.smoke = smoke;
	}

	@Override
	public void onDrinkClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.DRINK);
	}

	@Override
	public void onJobEdited(String job) {
		// TODO Auto-generated method stub
		this.job = job;
	}

	@Override
	public void onHeightEdited(int height) {
		// TODO Auto-generated method stub
		this.height = height;
	}

	@Override
	public void onMajorClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.MAJOR);
	}

	@Override
	public void onCoupleCountClick() {
		// TODO Auto-generated method stub
		showInfoSelectPopup(JoinInfoSelectItems.COUPLECOUNT);
	}

	@Override
	public void onConfirmClick() {
		// TODO Auto-generated method stub
		if (isComfirmed())
			callback.goNext(2);
	}

	@Override
	public void onConfirmed(int requestCode,
			ArrayList<Map<String, Object>> confirmedItems) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case JoinInfoSelectItems.BLOOD_TYPE:
			bloodType = (String) confirmedItems.get(0).get("title");
			layout.setBloodType(bloodType);
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

			characterConstText = String.format("%d|%d|%d", characters.get(0),
					characters.get(1), characters.get(2));

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
