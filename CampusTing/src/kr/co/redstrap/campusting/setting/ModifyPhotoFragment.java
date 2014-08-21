package kr.co.redstrap.campusting.setting;

import java.io.File;
import java.util.ArrayList;

import kr.co.redstrap.campusting.common.ListDialogFragment;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.constant.CampusTingConstant.RequestCodes;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ModifyPhotoFragment extends Fragment implements ModifyPhotoLayout.Callback {

	private ModifyPhotoLayout layout;
	
	private ArrayList<String> profileImages = new ArrayList<String>();
	
	public ArrayList<String> getProfileImages() {
		return profileImages;
	}
	
	private int photoCount;
	
	private int loadPhotoNum = -1;
	
	public void setPhotoCount(int count) {
		photoCount = count;
		// 20140812 chanroid 이걸로 뭔가 로딩을 해야함
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		layout = new ModifyPhotoLayout(getActivity());
		layout.setCallback(this);
		
		return layout.getView();
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
			cropIntent.setDataAndType(createSaveCaptureFile(loadPhotoNum),
					"image/*");
		cropIntent.putExtra("aspectX", 1);
		cropIntent.putExtra("aspectY", 1);
		cropIntent.putExtra("outputX", 200);
		cropIntent.putExtra("outputY", 200);
		cropIntent.putExtra("output", createSaveCropFile(loadPhotoNum));
		cropIntent.putExtra("scale", true);
		startActivityForResult(cropIntent,
				CampusTingConstant.RequestCodes.CROP_PICTURE);
	}

	private Uri createSaveCaptureFile(int index) {
		Uri uri;
		String url = "campusting_tmp_capture_photo_" + index + ".jpg";
		uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				url));
		return uri;
	}

	private Uri createSaveCropFile(int index) {
		Uri uri;
		String url = "campusting_tmp_crop_photo" + index + ".jpg";
		uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				url));
		return uri;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("결과", requestCode + " // " + resultCode);
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

				if (profileImages.size() > loadPhotoNum) {
					profileImages
							.set(loadPhotoNum, data.getData().getPath());
				} else {
					profileImages.add(data.getData().getPath());
				}
				layout.setImages(profileImages);
			}
		}
	}
	
	@Override
	public void onProfileImageClick(int index) {
		// TODO Auto-generated method stub

		loadPhotoNum = index;

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
	
}
