package kr.co.redstrap.campusting.main;

import java.util.ArrayList;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.main.AlertListLayout.Callback;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class AlertListActivity extends Activity implements Callback {

	private class MessageItem {
		public String message;
		public long time;
		public JSONObject alertInfo;
		public String action;
		public int userNum; // 없을 수도 있음
		public String url; // 없을 수도 있음
		public int building;
	}
	
	private ArrayList<MessageItem> messageItems = new ArrayList<MessageItem>();
	
	private AlertListLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new AlertListLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
		
		loadAlertList();
	}
	
	private void loadAlertList() {
		CTJSONSyncTask task = new CTJSONSyncTask();
		
		task.addHttpParam("userNum", LoginInfo.getInstance(this).getUserNum());
		task.addCallback(new CTSyncTaskCallback.Stub() {
			@Override
			public void onStartTask(AbsCTSyncTask<String, Object> task) {
				// TODO Auto-generated method stub
				super.onStartTask(task);
				layout.showLoading(null);
			}
			@Override
			public void onErrorTask(AbsCTSyncTask<String, Object> task,
					ErrorResult error) {
				// TODO Auto-generated method stub
				super.onErrorTask(task, error);
				layout.dismissLoading();
				layout.showErrorDialog(error, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						finish();
					}
				});
			}
			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object result) {
				// TODO Auto-generated method stub
				super.onSuccessTask(task, result);
				
				JSONObject json = (JSONObject) result;
				try {
					JSONArray list = json.getJSONArray("list");
					
					for (int i = 0; i < list.length(); i++) {
						JSONObject object = list.getJSONObject(i);
						MessageItem item = new MessageItem();
						item.action = object.getString("action");
						item.alertInfo = object.getJSONObject("alertInfo");
						item.building = object.getInt("building");
						item.message = object.getString("message");
						item.time = object.getLong("time");
						if (!object.isNull("url"))
							item.url = object.getString("url");
						if (!object.isNull("userNum"))
							item.userNum = object.getInt("userNum");
						messageItems.add(item);
					}
					
					// 20140821 chanroid 어댑터 설정하거나 하는 과정 필요. 서버작업 이후
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					onErrorTask(task, ErrorResult.resultFromException(e));
				}
			}
		});
		
		task.executeParallel("alertMessage");
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}
	
}
