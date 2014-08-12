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
import android.widget.Button;
import android.widget.TextView;

public class IdCardFragment extends Fragment implements IdCardLayout.Callback {

	private IdCardLayout layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		layout = new IdCardLayout(getActivity());
		layout.setCallback(this);

		loadUserInfo();
		loadCharamingPoint();

		return layout.getView();
	}
	
	private void loadCharamingPoint() {
		CTJSONSyncTask profileSync = new CTJSONSyncTask();
		CTJSONSyncTask faceSync = new CTJSONSyncTask();
		
		profileSync.addHttpParam("userNum", LoginInfo.getInstance(getActivity()).getUserNum());
		profileSync.addHttpParam("charmingType", 0);
		
		faceSync.addHttpParam("userNum", LoginInfo.getInstance(getActivity()).getUserNum());
		faceSync.addHttpParam("charmingType", 1);
		
		profileSync.addCallback(new CTSyncTaskCallback.Stub() {
			@Override
			public void onErrorTask(AbsCTSyncTask<String, Object> task,
					ErrorResult error) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object result) {
				// TODO Auto-generated method stub
				try {
					settingCharmingpointLayout((JSONObject) result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		faceSync.addCallback(new CTSyncTaskCallback.Stub() {
			@Override
			public void onErrorTask(AbsCTSyncTask<String, Object> task,
					ErrorResult error) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object result) {
				// TODO Auto-generated method stub
				try {
					settingCharmingpointLayout((JSONObject) result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		profileSync.executeParallel("feel");
		faceSync.executeParallel("feel");
		
	}
	
	private void settingCharmingpointLayout(JSONObject result) throws JSONException {
		int type = result.getInt("charmingType");
		int point = result.getInt("charmingPoint");
		long time = result.getLong("time");
		int percent = result.getInt("charmingPercent");
		if (type == 0) {
			layout.setProfileCharmingPointText(String.format("당신의 프로필호감도는 평균 %i점으로\n상위 %i%입니다.", point, percent));
			layout.setProfileCharmingPointButtonText(time + "");
		} else if (type == 1) {
			layout.setFaceCharmingPointText(String.format("당신의 외모호감도는 평균 %i점으로\n상위 %i%입니다.", point, percent));
			layout.setFaceCharmingPointBtnText(time + "");
		}
	}

	private void loadUserInfo() {

		CTJSONSyncTask task = new CTJSONSyncTask();

		task.addHttpParam("univcardType", 0); // 서버작업 완료되면 삭제
		task.addHttpParam("userNum", LoginInfo.getInstance(getActivity())
				.getUserNum());
		task.addHttpParam("targetUserNum", LoginInfo.getInstance(getActivity())
				.getUserNum());

		task.addCallback(new CTSyncTaskCallback.Stub() {

			@Override
			public void onStartTask(AbsCTSyncTask<String, Object> task) {
				// TODO Auto-generated method stub
				layout.showLoading("Loading...");
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
		String nickName = result.getString("nickName");
		int photoCount = result.getInt("photoCount");
		String simpleIntro = result.getString("simpleIntro");
		String birth = result.getString("birth");
		// 20140812 chanroid 생일 관련 작업은 서버 값 변경되면 진행
		int local = result.getInt("local");
		String job = result.getString("confirmedJob");
		int character = result.getInt("character");
		int height = result.getInt("height");
		int body = result.getInt("body");
		boolean gender = "Y".equals(result.getInt("gender"));
		int univNum = -1;
		if (!"".equals(result.getString("univNum"))) {
			univNum = result.getInt("univNum");
		}
		int majorNum = -1;
		if (!"".equals(result.getString("majorNum"))) {
			majorNum = result.getInt("majorNum");
		}

		String bloodType = result.getString("bloodType");
		int drink = result.getInt("drink");
		boolean smoke = "Y".equals(result.getString("smoke"));
		int coupleCount = result.getInt("coupleCount");
		int religion = result.getInt("religion");

		JSONArray hobbies = new JSONArray();
		if (!result.isNull("hobby"))
			hobbies = result.getJSONArray("hobby");
		JSONArray idealTypes = new JSONArray();
		if (!result.isNull("idealType"))
			idealTypes = result.getJSONArray("idealType");

		String confirmedUnivName = result.getString("confirmedUnivName");
		String confirmedUnivEmail = result.getString("confirmedUnivEmail");
		boolean confirmedUnivPhoto = "Y".equals("confirmedUnivPhoto");

		// 20140810 chanroid 일부 편집후 표시해야 할 객체들 구현 생략함
		// 사진표시 생략 (서버구현 필요)
		layout.setSimpleintroText(simpleIntro);
		layout.setProfileSimpleIntroDescText(simpleIntro);
		// 생일표시 - 형식이 맞지 않음
		String simpleinfoFormat = "%d세,%s,%s";
		layout.setSimpleinfoText(String.format(simpleinfoFormat, 27,
				JoinInfoSelectItems.local[local], job));
		layout.setProfileCharacterText(JoinInfoSelectItems.characters[character]);
		// 키 표시 생략 (표시할 칸 없음)
		layout.setProfileBodyText(JoinInfoSelectItems.body[body]);
		// 학교 표시 생략 (데이터베이스 필요)
		layout.setProfileSchoolText(confirmedUnivName);
		layout.setNicknameText(nickName);
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
