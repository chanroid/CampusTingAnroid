package kr.co.redstrap.campusting.join.frag;

import java.io.File;
import java.util.ArrayList;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.ListDialogFragment;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.join.AbsJoinFrag;
import kr.co.redstrap.campusting.util.CircleDrawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class PictureFrag extends AbsJoinFrag implements PictureLayout.Callback {

	private int targetPosition;
	// 리스너
	private PictureLayout layout;

	public ArrayList<String> profileImageList = new ArrayList<String>();
	public int loadPhotoNum = -1;

	public PictureFrag() {
		super();
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("PictureFrag :: onResume", "onResume");
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
		// 20140807 chanroid crop intent에 문제가 있어서 실행이 안됨
		// handle activity not found 어쩌고 저쩌고
		Log.i("uri", "" + intent.getData());
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		cropIntent.setData(intent.getData());
		cropIntent.putExtra("aspectX", 1);
		cropIntent.putExtra("aspectY", 1);
		cropIntent.putExtra("outputX", 100);
		cropIntent.putExtra("outputY", 100);
		cropIntent.putExtra("return-data", true);
		cropIntent.putExtra("scale", true);
		startActivityForResult(cropIntent,
				CampusTingConstant.RequestCodes.CROP_PICTURE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("결과", requestCode + " // " + resultCode);
		if (resultCode == Activity.RESULT_OK) { // 정상결과인 경우
			if ((requestCode == CampusTingConstant.RequestCodes.PICK_CAMERA || requestCode == CampusTingConstant.RequestCodes.PICK_GALLERY)) { // 사진
																																				// 선택
				Log.i("onActivityResult", data.getData() + "");
				crop(data);
			} else if (requestCode == CampusTingConstant.RequestCodes.CROP_PICTURE) { // 크롭
				Log.i("onActivityResult", data.getData() + "");
				Log.i("onActivityResult", data.getExtras() + "");
				Log.i("onActivityResult", data.getParcelableExtra("data") + "");
				if (profileImageList.size() > targetPosition) {
					profileImageList.set(targetPosition, data.getData()
							.toString());
				} else {
					profileImageList.add(data.getData().toString());
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
							Uri.parse(profileImageList.get(i)));
					layout.getImageHolder().get(i)
							.setImageDrawable(new CircleDrawable(bm));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				layout.setEmptyIcon(i);
			}
		}

		if (pictureSize == 0) {

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
		}
	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
//		return profileImageList.size() > 1;
		return true;
		// 20140808 chanroid 에러가 나는 관계로 일단 그냥 넘어감
	}
}
