package kr.co.redstrap.campusting.join.frag;

import java.io.File;
import java.util.ArrayList;

import kr.co.redstrap.campusting.common.ListDialogFragment;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.constant.CampusTingConstant.RequestCodes;
import kr.co.redstrap.campusting.join.AbsJoinFrag;
import kr.co.redstrap.campusting.util.CircleDrawable;
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

public class PictureFrag extends AbsJoinFrag implements PictureLayout.Callback {

	// 리스너
	private PictureLayout layout;

	public ArrayList<String> profileImageList = new ArrayList<String>();
	public int loadPhotoNum = -1;

	public PictureFrag() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("PictureFrag :: onCreateView", "onCreateView");
		layout = new PictureLayout(getActivity());

		// 리스너 셋팅
		layout.setPictureClickListener(this);

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
		cropIntent.putExtra("outputX", 100);
		cropIntent.putExtra("outputY", 100);
		cropIntent.putExtra("output", createSaveCropFile(loadPhotoNum));
		cropIntent.putExtra("scale", true);
		startActivityForResult(cropIntent,
				CampusTingConstant.RequestCodes.CROP_PICTURE);
	}

	private Uri createSaveCaptureFile(int index) {
		Uri uri;
		String url = "campusting_tmp_capture_join_" + index + ".jpg";
		uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				url));
		return uri;
	}

	private Uri createSaveCropFile(int index) {
		Uri uri;
		String url = "campusting_tmp_crop_join" + index + ".jpg";
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

				if (profileImageList.size() > loadPhotoNum) {
					profileImageList
							.set(loadPhotoNum, data.getData().getPath());
				} else {
					profileImageList.add(data.getData().getPath());
				}
				setPictures();
			}
		}
	}

	/**
	 * 사진 배열
	 * 
	 * @throws Exception
	 */
	private void setPictures() {
		int viewSize = layout.getImageHolder().size();
		int pictureSize = profileImageList.size();
		for (int i = 0; i < viewSize; i++) {
			layout.getImageHolder().get(i).setBackground(null);
			if (i < pictureSize) {
				try {
					Bitmap bm = Media.getBitmap(
							actContext.getContentResolver(),
							Uri.fromFile(new File(profileImageList.get(i))));
					layout.getImageHolder().get(i)
							.setImageDrawable(new CircleDrawable(bm));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				layout.setEmptyIcon(i);
			}
		}
	}

	@Override
	public void onPictureClick(int count) {
		// TODO Auto-generated method stub
		loadPhotoNum = count;

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
	public void onPictureConfirmClick() {
		// TODO Auto-generated method stub
		if (isComfirmed())
			callback.goNext(3);
		else {
			// 20140807 chanroid 사진 더 넣으라고 뭐라도 알려줄것
			layout.toast("사진을 최소 2장 이상 설정해 주세요.", Toast.LENGTH_LONG);
		}
	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
		if (profileImageList == null)
			return false;
		return profileImageList.size() > 1;
		// 20140808 chanroid 에러가 나는 관계로 일단 그냥 넘어감
	}
}
