package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.LoginInfo;
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
		
		CTJSONSyncTask task = new CTJSONSyncTask();
		
		task.addHttpParam("userNum", LoginInfo.getInstance(getActivity()).getUserNum());
		task.addHttpParam("univCardType", 1);
		
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
		
		return layout.getView();
	}

	private void settingIdCardInfo(JSONObject result) throws JSONException {
		// TODO Auto-generated method stub
		
		int photoCount = result.getInt("photoCount");
		String simpleIntro = result.getString("simpleIntro");
		String birth = result.getString("birth");
		int local = result.getInt("local");
		String job = result.getString("job");
		int character = result.getInt("character");
		int height = result.getInt("height");
		int body = result.getInt("body");
		int univNum = result.getInt("univNum");
		int majorNum = result.getInt("majorNum");
		String bloodType = result.getString("bloodType");
		int drink = result.getInt("drink");
		boolean smoke = result.getBoolean("smoke");
		int coupleCount = result.getInt("coupleCount");
		
		JSONArray hobbies = result.getJSONArray("hobby");
		JSONArray idealTypes = result.getJSONArray("idealType");
		JSONArray religion = result.getJSONArray("religion");
		
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
