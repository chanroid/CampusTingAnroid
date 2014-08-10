package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.main.MatchingResultLayout.Callback;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MatchingResultActivity extends Activity implements Callback {
	
	private MatchingResultLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new MatchingResultLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
		
		loadMatchingResult();
	}
	
	private void loadMatchingResult() {
		int buildingNum = getIntent().getIntExtra("building", -1);
		if (buildingNum == -1) {
			Toast.makeText(this, "잘못된 요청입니다.", Toast.LENGTH_LONG).show();
			finish();
		}
		
		CTJSONSyncTask task = new CTJSONSyncTask();
		
		task.addHttpParam("userNum", LoginInfo.getInstance(this).getUserNum());
		task.addHttpParam("building", buildingNum);
		
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
					settingMatchingResultInfo((JSONObject) result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					layout.showErrorDialog(ErrorResult.resultFromException(e));
				}
			}
		});
		
		task.executeParallel("searchForServer");
	}
	
	private void settingMatchingResultInfo(JSONObject json) throws JSONException {
		JSONArray list = json.getJSONArray("list");
		
		int user1 = list.getInt(0);
		int user2 = list.getInt(1);
		
		loadMatchingUserInfo(0, user1);
		loadMatchingUserInfo(1, user2);
	}
	
	private void loadMatchingUserInfo(final int index, int user) {
		CTJSONSyncTask task = new CTJSONSyncTask();
		
		task.addHttpParam("userNum", user);
		task.addHttpParam("univcardType", 0);
		
		task.addCallback(new CTSyncTaskCallback<String, Object>() {

			@Override
			public void onStartTask(AbsCTSyncTask<String, Object> task) {
				// TODO Auto-generated method stub
				
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
				layout.showErrorDialog(error);
			}

			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object orignresult) {
				// TODO Auto-generated method stub

				JSONObject result = (JSONObject) orignresult;
				
				// 20140810 chanroid 얻을 수 있는 모든 데이터를 우선 변수로 할당
				try {
					String nickName = result.getString("nickName");
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

					layout.setSimpleIntroText(index, simpleIntro);
					layout.setNickName(index, nickName);
					// 프로필 텍스트 생략
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onIdCardClick(int index) {
		// TODO Auto-generated method stub
		// 20140810 chanroid 학생증 보기 액티비티 따로 만들어야 함
	}
}
