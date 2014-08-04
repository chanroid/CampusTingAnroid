package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.join.AbsJoinFrag;
import kr.co.redstrap.campusting.util.CircleDrawable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PictureFrag extends AbsJoinFrag implements PictureLayout.Callback {

	private int targetPosition;
	private int mainPicture;
	// 리스너
	private PictureLayout layout;

	public PictureFrag() {
		super();
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("PictureFrag :: onResume", "onResume");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
		startActivityForResult(pickCameraIntent, CampusTingConstant.RequestCodes.PICK_CAMERA);
	}

	/**
	 * 갤러리 인텐트 시작
	 */
	private void pickGallery() {
		Intent pickGalleryIntent = new Intent(Intent.ACTION_PICK);
		pickGalleryIntent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(pickGalleryIntent, CampusTingConstant.RequestCodes.PICK_GALLERY);
	}

	/**
	 * 크롭 인텐트 시작
	 * 
	 * @param uri
	 *            크롭할 대상의 Uri
	 */
	private void crop(Uri uri) {
		Log.i("uri", "" + uri);
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		cropIntent.setData(uri);
		cropIntent.putExtra("aspectX", 1);
		cropIntent.putExtra("aspectY", 1);
		cropIntent.putExtra("scale", true);
		startActivityForResult(cropIntent, CampusTingConstant.RequestCodes.CROP_PICTURE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("결과", requestCode + " // " + resultCode);
		if (resultCode == Activity.RESULT_OK) { // 정상결과인 경우
			if ((requestCode == CampusTingConstant.RequestCodes.PICK_CAMERA || requestCode == CampusTingConstant.RequestCodes.PICK_GALLERY)) { // 사진 선택
				Log.i("onActivityResult", data.getData() + "");
				crop(data.getData());
			} else if (requestCode == CampusTingConstant.RequestCodes.CROP_PICTURE) { // 크롭
				Log.i("onActivityResult", data.getData() + "");
				Log.i("onActivityResult", data.getExtras() + "");
				Log.i("onActivityResult", data.getParcelableExtra("data") + "");
				if (actContext.pictureList.size() > targetPosition) {
					actContext.pictureList.set(targetPosition, data.getData().toString());
				} else {
					actContext.pictureList.add(data.getData().toString());
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
		int pictureSize = actContext.pictureList.size();
		for (int i = 0; i < viewSize; i++) {
			layout.getImageHolder().get(i).setBackground(null);
			if (i < pictureSize) {
				try {
					Bitmap bm = Media.getBitmap(actContext.getContentResolver(), Uri.parse(actContext.pictureList.get(i)));
					layout.getImageHolder().get(i).setImageDrawable(new CircleDrawable(bm));
					if (i == mainPicture) {
						layout.getImageHolder().get(i).setBackgroundResource(R.color.cherry);
					}
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
		
	}

	@Override
	public void onPictureConfirmClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
		return true;
	}
}
