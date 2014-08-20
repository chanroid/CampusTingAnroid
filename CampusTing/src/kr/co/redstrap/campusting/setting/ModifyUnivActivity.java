package kr.co.redstrap.campusting.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.ListDialogFragment;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.constant.CampusTingConstant.RequestCodes;
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
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class ModifyUnivActivity extends FragmentActivity implements ModifyUnivLayout.Callback {
	
	private ModifyUnivLayout layout;
	
	private int univState;
	private String imagePath;
	private String univMail = "";
	private String univMailCode;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		layout = new ModifyUnivLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
	}
	
	private void sendUnivConfirm() {
		if ("".equals(univMail)) {
			layout.toast("학교 이메일을 입력해주세요.", Toast.LENGTH_LONG);
		} else if ("".equals(univMailCode) || imagePath == null) {
			layout.toast("학교 메일 인증 또는 학생증 사진 인증 중에 하나를 반드시 해주세요.", Toast.LENGTH_SHORT);
		} else {
			CTJSONSyncTask task = new CTJSONSyncTask();
			task.addHttpParam("userNum", LoginInfo.getInstance(this).getUserNum());
			task.addHttpParam("univMail", univMail);
			task.addHttpParam("univMailCode", univMailCode);
			task.addHttpFileParam("univCardPhoto", imagePath);
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
					layout.toast("학교 인증이 완료되었어요.", Toast.LENGTH_LONG);
					setResult(RESULT_OK);
					finish();
				}
			});
			task.executeParallel("univCertification");
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) { // 정상결과인 경우
			if ((requestCode == RequestCodes.PICK_CAMERA || requestCode == RequestCodes.PICK_GALLERY)) { // 사진
				// 선택
				if (data != null)
					Log.i("onActivityResult", data.getData() + "");
				// 카메라 캡쳐로 온 경우 intent가 null이므로 미리 만들어 두었던 파일 경로를 참조해야 함
				crop(data);
			} else if (requestCode == RequestCodes.CROP_PICTURE) { // 크롭
																	// 선택

				if (data != null) {
					Log.i("onActivityResult", data.getData() + "");
					Log.i("onActivityResult", data.getExtras() + "");
					Log.i("onActivityResult", data.getParcelableExtra("data")
							+ "");
				}

				imagePath = data.getData().getPath();

				try {
					Bitmap bm = Media.getBitmap(getContentResolver(),
							Uri.fromFile(new File(imagePath)));
					layout.setUnivCardImage(bm);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
		task.addHttpParam("userId", LoginInfo.getInstance(this).getUserId());
		task.addHttpParam("userNum", LoginInfo.getInstance(this).getUserNum());
		// 20140820 chanroid NullPointerException
		
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
		// 20140820 chanroid 여기도 좀 이상한듯
	}

	/**
	 * 카메라 인텐트 시작
	 */
	private void pickCamera() {
		Intent pickCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		pickCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
				createSaveCaptureFile());
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

	/**
	 * 크롭 인텐트 시작
	 * 
	 * @param uri
	 *            크롭할 대상의 Uri
	 */
	private void crop(Intent intent) {
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		if (intent != null && intent.getData() != null)
			cropIntent.setDataAndType(intent.getData(), "image/*");
		else
			cropIntent.setDataAndType(createSaveCaptureFile(),
					"image/*");
		cropIntent.putExtra("aspectX", 1);
		cropIntent.putExtra("aspectY", 1);
		cropIntent.putExtra("outputX", 100);
		cropIntent.putExtra("outputY", 100);
		cropIntent.putExtra("output", createSaveCropFile());
		cropIntent.putExtra("scale", true);
		startActivityForResult(cropIntent,
				CampusTingConstant.RequestCodes.CROP_PICTURE);
	}

	private Uri createSaveCaptureFile() {
		Uri uri;
		String url = "campusting_tmp_capture_univ" + ".jpg";
		uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				url));
		return uri;
	}

	private Uri createSaveCropFile() {
		Uri uri;
		String url = "campusting_tmp_crop_univ" + ".jpg";
		uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				url));
		return uri;
	}

	@Override
	public void onUnivCardImageClick() {
		// TODO Auto-generated method stub
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
		dialog.show(getSupportFragmentManager(), null);
	}

	@Override
	public void onUnivMailChanged(String mail) {
		// TODO Auto-generated method stub
		this.univMail = mail;
	}

	@Override
	public void onUnivMailCodeRequestClick() {
		// TODO Auto-generated method stub
		requestMailConfirmCode();
	}

	@Override
	public void onUnivMailCodeConfirmClick() {
		// TODO Auto-generated method stub
		confirmMailConfirmCode();
	}

	@Override
	public void onUnivMailConfirmClick() {
		// TODO Auto-generated method stub
		sendUnivConfirm();
	}

	@Override
	public void onUnivCardConfirmClick() {
		// TODO Auto-generated method stub
		sendUnivConfirm();
	}

	@Override
	public void onUnivStateChanged(int position) {
		// TODO Auto-generated method stub
		this.univState = position;
	}

	@Override
	public void onUnivChanged(int num) {
		// TODO Auto-generated method stub
		// 20140820 chanroid 학교 db 작업 완료되면 진행
	}

	@Override
	public void onUnivMailCodeChanged(String string) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConfirmClick() {
		// TODO Auto-generated method stub
		sendUnivConfirm();
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}
}
