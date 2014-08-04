package kr.co.redstrap.campusting.common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * 액티비티, 프래그먼트 등의 레이아웃 구성 기초 클래스
 * 
 * @author rbi_bi_3
 * 
 */
public abstract class AbsCTLayout {

	public AbsCTLayout(Context ctx) {
		mContext = ctx;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRoot = mInflater.inflate(getRootViewId(), null);
		inflateViews();

		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setCancelable(false);
	}

	private View mRoot;
	private Context mContext;
	private LayoutInflater mInflater;

	// 미리 생성해두고 필요시 사용
	private ProgressDialog mProgressDialog;

	public void showLoading(int messageId) {
		showLoading(mContext.getString(messageId));
	}

	public void showLoading(CharSequence message) {
		mProgressDialog.setMessage(message);

		if (!mProgressDialog.isShowing())
			mProgressDialog.show();
	}

	public void dismissLoading() {
		if (mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}

	public void showErrorDialog(ErrorResult error) {
		new AlertDialog.Builder(getContext()).setMessage(error.message)
				.setTitle("에러").setPositiveButton("확인", null).show();
	}

	public LayoutInflater getLayoutInflater() {
		return mInflater;
	}

	public View getView() {
		return mRoot;
	}

	public Context getContext() {
		return mContext;
	}

	public View findViewById(int id) {
		return mRoot.findViewById(id);
	}

	public void toast(int id, int length) {
		toast(mContext.getString(id), length);
	}

	public void toast(String message, int length) {
		Toast.makeText(mContext, message, length).show();
	}

	public abstract int getRootViewId();

	public abstract void inflateViews();

}
