package kr.co.redstrap.campusting.join.frag;

import java.io.File;
import java.util.ArrayList;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.ListDialogFragment;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.constant.CampusTingConstant.RequestCodes;
import kr.co.redstrap.campusting.join.AbsJoinFrag;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ConfirmUnivFrag extends AbsJoinFrag implements
		ConfirmUnivFragLayout.Callback {

	private ConfirmUnivFragLayout layout;

	public int univNum = -1;
	public int univState = 0;
	public String univMail = "";
	public String univMailCode = "";
	public String univCardImage = "";
	public String jobImage = "";

	private int loadPhotoNum = -1;

	public boolean univConfirmed;
	public boolean univPhotoConfirmed;
	public boolean jobConfirmed;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		layout = new ConfirmUnivFragLayout(actContext);
		layout.setCallback(this);

		return layout.getView();
	}

	private void confirmMailConfirmCode() {
		CTJSONSyncTask task = new CTJSONSyncTask();

		task.addHttpParam("acceptNum", univMailCode);
		task.addHttpParam("univMail", univMail);

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
				layout.showErrorDialog(error);
			}

			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object result) {
				// TODO Auto-generated method stub
				super.onSuccessTask(task, result);
				layout.dismissLoading();

				JSONObject json = (JSONObject) result;
				try {
					if ("Y".equals(json.getString("acceptYn"))) {
						layout.toast("학교 이메일 인증이 완료되었어요.", Toast.LENGTH_LONG);
						layout.setConfirmedUnivMail(true);
					} else {
						layout.showErrorDialog(new ErrorResult(0,
								"학교 메일 인증에 실패했어요. 다시 시도해 주세요."));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					layout.showErrorDialog(ErrorResult.resultFromException(e));
				}

			}
		});

		task.execute("emailAuthInput");
	}

	private void requestMailConfirmCode() {
		CTJSONSyncTask task = new CTJSONSyncTask();

		task.addHttpParam("univMail", univMail);
		task.addHttpParam("userId", actContext.normalFrag.getInfo().email);

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
				layout.showErrorDialog(error);
			}

			@Override
			public void onSuccessTask(AbsCTSyncTask<String, Object> task,
					Object result) {
				// TODO Auto-generated method stub
				super.onSuccessTask(task, result);
				layout.dismissLoading();
				layout.toast("입력하신 학교 메일로 인증번호가 발송되었어요.", Toast.LENGTH_LONG);
				// 20140819 chanroid 승인번호가 날아오지만 로직상 사용하면 안됨
			}
		});
		task.execute("emailAuth");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if ((requestCode == RequestCodes.PICK_CAMERA || requestCode == RequestCodes.PICK_GALLERY)) { // 사진
				// 선택
				if (data != null)
					Log.i("onActivityResult", data.getData() + "");
				// 카메라 캡쳐로 온 경우 intent가 null이므로 미리 만들어 두었던 파일 경로를 참조해야 함
				crop(data);
			} else if (requestCode == RequestCodes.CROP_PICTURE) { // 크롭
									
				if (data != null) {
					Log.i("onActivityResult", data.getData() + "");
					Log.i("onActivityResult", data.getExtras() + "");
					Log.i("onActivityResult", data.getParcelableExtra("data")
							+ "");
				}

				if (loadPhotoNum == 0)
					univCardImage = data.getData().getPath();
				else if (loadPhotoNum == 1)
					jobImage = data.getData().getPath();

				setPictures();
			}
		}
	}

	private void setPictures() {
		try {
			Bitmap univImage = Media.getBitmap(actContext.getContentResolver(),
					Uri.fromFile(new File(univCardImage)));
			if (univImage != null)
				layout.setUnivCardImage(univImage);

			Bitmap jobcardImage = Media.getBitmap(
					actContext.getContentResolver(),
					Uri.fromFile(new File(jobImage)));
			if (jobImage != null)
				layout.setJobCardImage(jobcardImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void crop(Intent intent) {
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		if (intent != null && intent.getData() != null)
			cropIntent.setDataAndType(intent.getData(), "image/*");
		else
			cropIntent.setDataAndType(createSaveCaptureFile(loadPhotoNum),
					"image/*");
		cropIntent.putExtra("aspectX", 1.4);
		cropIntent.putExtra("aspectY", 1);
		cropIntent.putExtra("outputX", 280);
		cropIntent.putExtra("outputY", 200);
		cropIntent.putExtra("output", createSaveCropFile(loadPhotoNum));
		cropIntent.putExtra("scale", true);
		startActivityForResult(cropIntent,
				CampusTingConstant.RequestCodes.CROP_PICTURE);
	}

	private Uri createSaveCaptureFile(int index) {
		Uri uri;
		String url = "campusting_tmp_capture_confirm_" + index + ".jpg";
		uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				url));
		return uri;
	}

	private Uri createSaveCropFile(int index) {
		Uri uri;
		String url = "campusting_tmp_crop_confirm_" + index + ".jpg";
		uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				url));
		return uri;
	}

	/**
	 * 카메라 인텐트 시작
	 */
	private void pickCamera() {
		Intent pickCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		pickCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
				createSaveCaptureFile(loadPhotoNum));
		startActivityForResult(pickCameraIntent,
				CampusTingConstant.RequestCodes.PICK_CAMERA);
	}

	/**
	 * 갤러리 인텐트 시작
	 */
	private void pickGallery() {
		Intent pickGalleryIntent = new Intent(Intent.ACTION_PICK);
		pickGalleryIntent
				.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(pickGalleryIntent,
				CampusTingConstant.RequestCodes.PICK_GALLERY);
	}

	@Override
	public void onUnivCardImageClick() {
		// TODO Auto-generated method stub
		loadPhotoNum = 0;

		ArrayList<String> items = new ArrayList<String>();
		items.add("사진 촬영하기");
		items.add("갤러리에서 선택");

		ListDialogFragment dialog = new ListDialogFragment(items);
		dialog.setCallback(new ListDialogFragment.Callback() {
			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				if (position == 0)
					pickCamera();
				else if (position == 1)
					pickGallery();
			}
		});
		dialog.show(getActivity().getSupportFragmentManager(), null);
	}

	@Override
	public void onUnivChanged(int num) {
		// TODO Auto-generated method stub
		univState = num;
	}

	@Override
	public void onUnivMailChanged(String mail) {
		// TODO Auto-generated method stub
		univMail = mail;
	}

	@Override
	public void onUnivMailCodeRequestClick() {
		// TODO Auto-generated method stub
		if (!"".equals(univMail)) {
			requestMailConfirmCode();
		}
	}

	@Override
	public void onUnivMailCodeConfirmClick() {
		// TODO Auto-generated method stub
		confirmMailConfirmCode();
	}

	@Override
	public void onJobCardImageClick() {
		// TODO Auto-generated method stub
		loadPhotoNum = 1;

		ArrayList<String> items = new ArrayList<String>();
		items.add("사진 촬영하기");
		items.add("갤러리에서 선택");

		ListDialogFragment dialog = new ListDialogFragment(items);
		dialog.setCallback(new ListDialogFragment.Callback() {
			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				if (position == 0)
					pickCamera();
				else if (position == 1)
					pickGallery();
			}
		});
		dialog.show(getActivity().getSupportFragmentManager(), null);
	}

	@Override
	public void onConfirmClick() {
		// TODO Auto-generated method stub
		if (isComfirmed())
			callback.goNext(4);
		else {
			// 20140808 chanroid 뭔가 안됐다고 알려줘야함
		}
	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onUnivMailConfirmClick() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUnivCardConfirmClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onJobConfirmClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnivStateChanged(int position) {
		// TODO Auto-generated method stub
		univState = position;
	}

	@Override
	public void onUnivMailCodeChanged(String string) {
		// TODO Auto-generated method stub
		univMailCode = string;
	}

}
