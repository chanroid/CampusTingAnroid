package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.join.JoinInfoSelectItems;
import kr.co.redstrap.campusting.setting.ModifyProfileActivity;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IdCardFragment extends Fragment implements IdCardLayout.Callback {

	private IdCardLayout layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		layout = new IdCardLayout(getActivity());
		layout.setCallback(this);
		
		loadUserInfo();

		return layout.getView();
	}

	private void loadUserInfo() {

		CTJSONSyncTask task = new CTJSONSyncTask();

		task.addHttpParam("univcardType", 0); // 서버작업 완료되면 삭제
		task.addHttpParam("userNum", LoginInfo.getInstance(getActivity())
				.getUserNum());
		task.addHttpParam("targetUserNum", LoginInfo.getInstance(getActivity())
				.getUserNum());

		task.addCallback(new CTSyncTaskCallback<String, Object>() {

			@Override
			public void onStartTask(AbsCTSyncTask<String, Object> task) {
				// TODO Auto-generated method stub
				layout.showLoading("Loading...");
			}

			@Override
			public void onProgressTask(AbsCTSyncTask<String, Object> task,
					int progress) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onErrorTask(AbsCTSyncTask<String, Object> task,
					ErrorResult error) {
				// TODO Auto-generated method stub
				layout.dismissLoading();
				layout.showErrorDialog(error);
			}

			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object result) {
				// TODO Auto-generated method stub
				layout.dismissLoading();

				try {
					settingIdCardInfo((JSONObject) result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		task.executeParallel("user");

	}

	private void settingIdCardInfo(JSONObject result) throws JSONException {
		// TODO Auto-generated method stub

		// 얻을 수 있는 모든 데이터를 우선 변수로 할당
		// String nickName = result.getString("nickName");
		int photoCount = result.getInt("photoCount");
		String simpleIntro = result.getString("simpleIntro");
		String birth = result.getString("birth");
		int local = result.getInt("local");
		String job = result.getString("job");
		int character = result.getInt("character");
		int height = result.getInt("height");
		int body = result.getInt("body");
		int univNum = result.getInt("univName");
		int majorNum = result.getInt("majorNum");
		String bloodType = result.getString("bloodType");
		int drink = result.getInt("drink");
		boolean smoke = "Y".equals(result.getString("smoke"));
		int coupleCount = result.getInt("coupleCount");
		int religion = result.getInt("religion");

		JSONArray hobbies = result.getJSONArray("hobby");
		JSONArray idealTypes = result.getJSONArray("idealType");

		// 20140810 chanroid 일부 편집후 표시해야 할 객체들 구현 생략함
		// 사진표시 생략 (서버구현 필요)
		layout.setSimpleintroText(simpleIntro);
		layout.setProfileSimpleIntroDescText(simpleIntro);
		// 생일표시 생략 (표시할 칸 없음)
		layout.setProfileCharacterText(JoinInfoSelectItems.characters[character]);
		// 키 표시 생략 (표시할 칸 없음)
		layout.setProfileBodyText(JoinInfoSelectItems.body[body]);
		// 학교 표시 생략 (데이터베이스 필요)
		// layout.setNicknameText(nickName);
		// 학과 표시 생략 (표시할 칸 없음)
		layout.setProfileBloodText(bloodType);

		String drinksmokeFormat = "%s이고, 술은 %s.";
		String drinkText = JoinInfoSelectItems.drink[drink];
		String smokeText = smoke ? "흡연자" : "비흡연자";
		layout.setProfileDrinksmokeText(String.format(drinksmokeFormat,
				smokeText, drinkText));

		layout.setProfileCoupleCountText(JoinInfoSelectItems.couple[coupleCount]);
		// 취미 표시 생략
		// 이상형 표시 생략
		layout.setProfileReligionText(JoinInfoSelectItems.religion[religion]);

	}

	@Override
	public void onProfileImageClick(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onModifyProfileClick() {
		// TODO Auto-generated method stub
		startActivity(new Intent(getActivity(), ModifyProfileActivity.class));
	}

	@Override
	public void onShowFaceCharmingClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onShowProfileCharmingClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMainProfileImageClick() {
		// TODO Auto-generated method stub

	}

}
