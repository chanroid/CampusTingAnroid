package kr.co.redstrap.campusting.main;

import java.util.ArrayList;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HistoryFragment extends Fragment implements HistoryLayout.Callback {
	
	private HistoryLayout layout;
	
	private ArrayList<HistoryItem> historyItems = new ArrayList<HistoryItem>();
	
	
	public static final int REQUEST_ALL = 0;
	public static final int REQUEST_RECEIVED = 1;
	public static final int REQUEST_SENDED = 2;
	public static final int REQUEST_CONFIRMED = 3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		layout = new HistoryLayout(getActivity());
		layout.setCallback(this);
		
		historyItems = new ArrayList<HistoryItem>();
		
		loadHistoryList(REQUEST_ALL);
		
		return layout.getView();
	}
	
	private void loadHistoryList(int type) {
		CTJSONSyncTask task = new CTJSONSyncTask();
		
		task.addHttpParam("userNum", LoginInfo.getInstance(getActivity()).getUserNum());
		task.addHttpParam("tingRequestType", type);
		
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
					settingHistoryList((JSONObject) result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					layout.showErrorDialog(ErrorResult.resultFromException(e));
				}
			}
		});
		
		task.executeParallel("history");
	}
	
	public static class HistoryItem {
		public int tingNum;
		public int targetNum;
		public int requestType;
		public long time;
		public boolean isAccept;
		public boolean isChating;
		public boolean isDeleted;
		
	}
	
	private void settingHistoryList(JSONObject result) throws JSONException {
		historyItems.clear();
		
		JSONArray list = result.getJSONArray("list");
		
		for (int i = 0; i < list.length(); i++) {
			JSONObject tingObject = list.getJSONObject(i);
			HistoryItem tingItem = new HistoryItem();
			
			tingItem.tingNum = tingObject.getInt("tingNum");
			tingItem.targetNum = tingObject.getInt("targetNum");
			tingItem.requestType = tingObject.getInt("tingRequestType");
//			tingItem.time = tingObject.getLong("time");
			// 20140814 chanroid 서버에서 값 형식이 맞지 않음
			tingItem.isAccept = "Y".equals(tingObject.getString("isAcceptTing"));
			tingItem.isChating = "Y".equals(tingObject.getString("isChating"));
			tingItem.isDeleted = "Y".equals(tingObject.getString("isDeletedChating"));
			
			historyItems.add(tingItem);
		}
		
		// 20140811 chanroid 서버 동작하는거 확인되면 어댑터 만들고 리스트 아이템 만들고
	}

	@Override
	public void onAllTabClick() {
		// TODO Auto-generated method stub
		loadHistoryList(REQUEST_ALL);
	}

	@Override
	public void onReceiveTabClick() {
		// TODO Auto-generated method stub
		loadHistoryList(REQUEST_RECEIVED);
	}

	@Override
	public void onSendTabClick() {
		// TODO Auto-generated method stub
		loadHistoryList(REQUEST_SENDED);
	}

	@Override
	public void onConfirmTabClick() {
		// TODO Auto-generated method stub
		loadHistoryList(REQUEST_CONFIRMED);
	}

	@Override
	public void onTingItemClick(int index) {
		// TODO Auto-generated method stub
		
	}
}
