package kr.co.redstrap.campusting.join.frag;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.join.AbsJoinFrag;
import kr.co.redstrap.campusting.main.MainActivity;
import kr.co.redstrap.campusting.util.SHA256;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;

import org.apache.http.protocol.HTTP;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ConfirmUnivFrag extends AbsJoinFrag implements ConfirmUnivFragLayout.Callback {
	
	private ConfirmUnivFragLayout layout;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		layout = new ConfirmUnivFragLayout(actContext);
		layout.setCallback(this);
		
		return layout.getView();
	}

	@Override
	public void onUnivCardImageClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnivNameChanged(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnivMailChanged(String mail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnivMailCodeRequestClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnivMailCodeConfirmClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJobCardImageClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConfirmClick() {
		// TODO Auto-generated method stub

		CTJSONSyncTask task = new CTJSONSyncTask();
		
		task.addHttpParam("userId", "chanroid@gmail.com");
		task.addHttpParam("userPw", SHA256.getCipherText("campus"));
		task.addHttpParam("gender", Boolean.TRUE.toString());
		task.addHttpParam("birth", "19880811");
		task.addHttpParam("promoCode", "TESTCD");
		task.addHttpParam("local", "0");
		task.addHttpParam("character", "0|1|2");
		task.addHttpParam("body", "0");
		task.addHttpParam("height", "0");
		task.addHttpParam("bloodType", "B");
		task.addHttpParam("religion", "0");
		task.addHttpParam("smoke", "0");
		task.addHttpParam("drink", "1");
		task.addHttpParam("major", "1");
		task.addHttpParam("coupleCount", "0");
		task.addHttpParam("univMail", "chanroid@kit.ac.kr");
		
		// 그 밖에 사진도 넣어야 되는데
		
		try {
			task.addHttpParam("job", URLEncoder.encode("프로그래머", HTTP.UTF_8));
			task.addHttpParam("nickName", URLEncoder.encode("테스트", HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
				
				// 20140723 chanroid 일단 그냥 메인으로 넘어가게 설정. 나중에 액티비티 구현할때 마저 구현
				startActivity(new Intent(getActivity(), MainActivity.class));
				getActivity().finish();
			}
		});
		
		task.executeParallel("userPost");
	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
		return true;
	}
}
