package kr.co.redstrap.campusting.setting;

import java.util.ArrayList;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class ModifyProfileActivity extends FragmentActivity implements
		ModifyProfileLayout.Callback {

	private ModifyProfileLayout layout;

	private ModifyPhotoFragment modifyPhotoFragment;
	private ModifyMyProfileFragment modifyProfileFragment;
	private ModifyAppealFragment modifyAppealFragment;
	private ModifyIDCardSkinFragment modifyIDCardSkinFragment;

	private static final int FRAGMENT_CONTAINER = R.id.modifyprofileFragmentContainer;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		layout = new ModifyProfileLayout(this);
		layout.setCallback(this);

		setContentView(layout.getView());
		initFragment();
		switchFragment(modifyPhotoFragment);

		loadProfile();
	}

	private void initFragment() {
		modifyPhotoFragment = new ModifyPhotoFragment();
		modifyProfileFragment = new ModifyMyProfileFragment();
		modifyAppealFragment = new ModifyAppealFragment();
		modifyIDCardSkinFragment = new ModifyIDCardSkinFragment();

		FragmentTransaction trans = getSupportFragmentManager()
				.beginTransaction();
		trans.add(FRAGMENT_CONTAINER, modifyPhotoFragment);
		trans.add(FRAGMENT_CONTAINER, modifyProfileFragment);
		trans.add(FRAGMENT_CONTAINER, modifyAppealFragment);
		trans.add(FRAGMENT_CONTAINER, modifyIDCardSkinFragment);
		trans.commit();
	}

	private void switchFragment(Fragment fragment) {
		FragmentTransaction trans = getSupportFragmentManager()
				.beginTransaction();
		trans.hide(modifyPhotoFragment);
		trans.hide(modifyProfileFragment);
		trans.hide(modifyAppealFragment);
		trans.hide(modifyIDCardSkinFragment);

		trans.show(fragment);
		trans.commit();
	}

	private void loadProfile() {
		CTJSONSyncTask task = new CTJSONSyncTask();

		task.addHttpParam("univcardType", 0); // 서버작업 완료되면 삭제
		task.addHttpParam("userNum", LoginInfo.getInstance(this).getUserNum());
		task.addHttpParam("targetUserNum", LoginInfo.getInstance(this)
				.getUserNum());

		task.addCallback(new CTSyncTaskCallback<String, Object>() {
			@Override
			public void onStartTask(AbsCTSyncTask<String, Object> task) {
				// TODO Auto-generated method stub
				layout.showLoading(null);
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
					settingProfile((JSONObject) result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					layout.showErrorDialog(ErrorResult.resultFromException(e));
					e.printStackTrace();
				}
			}
		});

		task.executeParallel("user");
	}

	private void settingProfile(JSONObject result) throws JSONException {
		int photoCount = result.getInt("photoCount");
		String simpleIntro = result.getString("simpleIntro");
		String birth = result.getString("birth");
		int local = result.getInt("local");
		String job = result.getString("job");
		int height = result.getInt("height");
		int body = result.getInt("body");
		String nickName = result.getString("nickName");

		int univNum = -1;
		if (!"".equals(result.getString("univNum"))) {
			univNum = result.getInt("majorNum");
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
		if (!result.isNull("hobby")) {
			hobbies = result.getJSONArray("hobby");
		}

		JSONArray idealTypes = new JSONArray();
		if (!result.isNull("idealType")) {
			idealTypes = result.getJSONArray("idealType");
		}

		// JSONArray character = result.getJSONArray("character");

		String confirmedUnivName = result.getString("confirmedUnivName");
		String confirmedUnivEmail = result.getString("confirmedUnivEmail");
		boolean confirmedUnivPhoto = "Y".equals(result
				.getString("confirmedUnivPhoto"));
		boolean confirmedJob = !"0".equals(result.getString("confirmedJob"));
		boolean gender = result.getBoolean("gender");

		modifyPhotoFragment.setPhotoCount(photoCount);
		modifyAppealFragment.setOneLineText(simpleIntro);
		modifyProfileFragment.setBirth(birth);
		modifyProfileFragment.setLocal(local);
		modifyProfileFragment.setJob(job);
		modifyProfileFragment.setHeight(height);
		modifyProfileFragment.setBody(body);
		modifyProfileFragment.setNickName(nickName);
		if (univNum >= 0) {
			// modifyProfileFragment.setUnivNum(univNum);
			// 20140812 chanroid 학교 db 넘어가면 진행
		}
		if (majorNum >= 0) {
			modifyProfileFragment.setMajor(majorNum);
		}
		modifyProfileFragment.setBloodType(bloodType);
		modifyProfileFragment.setDrink(drink);
		modifyProfileFragment.setSmoke(smoke);
		modifyProfileFragment.setCoupleCount(coupleCount);
		modifyProfileFragment.setReligion(religion);

		for (int i = 0; i < hobbies.length(); i++) {
			modifyAppealFragment.setHobbyAppealText(i, hobbies.getString(i));
		}

		for (int i = 0; i < idealTypes.length(); i++) {
			modifyAppealFragment.setIdealTypeText(i, idealTypes.getString(i));
		}

		// 20140812 chanroid 아래 내용은 서버 확인 후 주석 해제

		// ArrayList<Integer> characterArray = new ArrayList<Integer>();
		// for (int i = 0; i < character.length(); i++) {
		// characterArray.add(character.getInt(i));
		// }
		// modifyProfileFragment.setCharacter(characterArray);

		modifyProfileFragment.setConfirmedUniv(confirmedUnivName,
				confirmedUnivEmail, confirmedUnivPhoto);
		modifyProfileFragment.setConfirmedJob(confirmedJob);
		modifyProfileFragment.setMan(gender);
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onPhotoTabClick() {
		// TODO Auto-generated method stub
		switchFragment(modifyPhotoFragment);
	}

	@Override
	public void onInfoTabClick() {
		// TODO Auto-generated method stub
		switchFragment(modifyProfileFragment);
	}

	@Override
	public void onAppealTabClick() {
		// TODO Auto-generated method stub
		switchFragment(modifyAppealFragment);
	}

	@Override
	public void onIDCardSkinTabClick() {
		// TODO Auto-generated method stub
		switchFragment(modifyIDCardSkinFragment);
	}

	@Override
	public void onConfirmClick() {
		// TODO Auto-generated method stub
		if (modifyPhotoFragment == null || modifyAppealFragment == null
				|| modifyProfileFragment == null) {
			finish();
			return;
		}

		String simpleIntro = modifyAppealFragment.getOneLineText();
		String birth = modifyProfileFragment.getBirth();
		int local = modifyProfileFragment.getLocal();
		String job = modifyProfileFragment.getJob();
		int height = modifyProfileFragment.getHeight();
		int body = modifyProfileFragment.getBody();
		// int univNum = modifyProfileFragment.getUnivNum();
		int majorNum = modifyProfileFragment.getMajor();
		String bloodType = modifyProfileFragment.getBloodType();
		int drink = modifyProfileFragment.getDrink();
		boolean smoke = modifyProfileFragment.isSmoke();
		int coupleCount = modifyProfileFragment.getCoupleCount();
		int religion = modifyProfileFragment.getReligion();

		String hobbies = modifyAppealFragment.getHobbyAppealText(0) + "|"
				+ modifyAppealFragment.getHobbyAppealText(1) + "|"
				+ modifyAppealFragment.getHobbyAppealText(2);
		String idealTypes = modifyAppealFragment.getIdealTypeText(0) + "|"
				+ modifyAppealFragment.getIdealTypeText(1) + "|"
				+ modifyAppealFragment.getIdealTypeText(2);
		String character = modifyProfileFragment.getCharacter().get(0) + "|"
				+ modifyProfileFragment.getCharacter().get(1) + "|"
				+ modifyProfileFragment.getCharacter().get(2);

		CTJSONSyncTask task = new CTJSONSyncTask();

		task.addHttpParam("userNum", LoginInfo.getInstance(this).getUserNum());
		task.addHttpParam("local", local);
		task.addHttpParam("birth", birth);
		task.addHttpParam("character", character);
		task.addHttpParam("body", body);
		task.addHttpParam("height", height);
		task.addHttpParam("bloodType", bloodType);
		task.addHttpParam("religion", religion);
		task.addHttpParam("smoke", String.valueOf(smoke));
		task.addHttpParam("drink", drink);
		task.addHttpParam("job", job);
		task.addHttpParam("majorNum", majorNum);
		task.addHttpParam("coupleCount", coupleCount);
		task.addHttpParam("simpleIntro", simpleIntro);
		task.addHttpParam("idealType", idealTypes);
		task.addHttpParam("hobby", hobbies);

		ArrayList<String> profileImages = modifyPhotoFragment.getProfileImages();
		int photoCount = profileImages.size();
		for (int i = 0; i < photoCount; i++) {
			task.addHttpFileParam("profilePhoto" + (i+1), profileImages.get(i));
		}

		task.addCallback(new CTSyncTaskCallback<String, Object>() {

			@Override
			public void onStartTask(AbsCTSyncTask<String, Object> task) {
				// TODO Auto-generated method stub
				layout.showLoading(null);
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
				Toast.makeText(ModifyProfileActivity.this, "프로필 수정이 완료되었습니다.",
						Toast.LENGTH_LONG).show();
				finish();
			}
		});

		task.executeParallel("userPut");
	}
}
