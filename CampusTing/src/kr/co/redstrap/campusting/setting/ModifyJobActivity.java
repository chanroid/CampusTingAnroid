package kr.co.redstrap.campusting.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import kr.co.redstrap.campusting.common.AbsCTSyncTask.CTSyncTaskCallback;
import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.common.ListDialogFragment;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.constant.CampusTingConstant.RequestCodes;
import kr.co.redstrap.campusting.util.web.CTJSONSyncTask;
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

public class ModifyJobActivity extends FragmentActivity implements ModifyJobLayout.Callback {
	
	private ModifyJobLayout layout;
	
	private String imagePath;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		layout = new ModifyJobLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
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
					layout.setJobCardImage(bm);
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
	
	private void sendJobConfirm() {
		CTJSONSyncTask task = new CTJSONSyncTask();
		
		task.addHttpParam("userNum", LoginInfo.getInstance(this).getUserNum());
		task.addHttpFileParam("jobPhoto", imagePath);
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
				layout.toast("직업 인증이 완료되었어요.", Toast.LENGTH_LONG);
				setResult(RESULT_OK);
				finish();
			}
		});
		task.execute("confirmJob");
		// 20140820 chanroid 아직 존재하지 않는 경로
		
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
		cropIntent.putExtra("aspectX", 1.4);
		cropIntent.putExtra("aspectY", 1);
		cropIntent.putExtra("outputX", 280);
		cropIntent.putExtra("outputY", 200);
		cropIntent.putExtra("output", createSaveCropFile());
		cropIntent.putExtra("scale", true);
		startActivityForResult(cropIntent,
				CampusTingConstant.RequestCodes.CROP_PICTURE);
	}

	private Uri createSaveCaptureFile() {
		Uri uri;
		String url = "campusting_tmp_capture_job" + ".jpg";
		uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				url));
		return uri;
	}

	private Uri createSaveCropFile() {
		Uri uri;
		String url = "campusting_tmp_crop_job" + ".jpg";
		uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				url));
		return uri;
	}

	@Override
	public void onJobCardImageClick() {
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
	public void onJobConfirmClick() {
		// TODO Auto-generated method stub
		if (imagePath != null)
			sendJobConfirm();
		else {
			layout.showErrorDialog(new ErrorResult(0, "직업인증 사진을 업로드 해 주세요."));
		}
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}
}
