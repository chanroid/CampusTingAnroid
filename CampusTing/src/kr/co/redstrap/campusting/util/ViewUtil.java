package kr.co.redstrap.campusting.util;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.R;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class ViewUtil {

	private final static Drawable iconGood = MainApp.appContext.getResources().getDrawable(R.drawable.icon_good);
	private final static Drawable iconBad = MainApp.appContext.getResources().getDrawable(R.drawable.icon_bad);

	public ViewUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 타겟 뷰를 1000밀리초 동안 비활성화
	 * 
	 * @param v
	 *            적용할 뷰
	 */
	public static void filterClick(final View view) {
		VibratorUtil.vibe();
		view.setEnabled(false);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				view.setEnabled(true);
			}
		}, 1000);
	}

	/**
	 * 타겟 뷰를 지정된 밀리초 동안 비활성화
	 * 
	 * @param v
	 *            적용할 뷰
	 * @param delayMillis
	 *            비활성화할 시간(밀리초)
	 */
	public static void filterClick(final View view, final long delayMillis) {
		VibratorUtil.vibe();
		view.setEnabled(false);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				view.setEnabled(true);
			}
		}, delayMillis);
	}

	/**
	 * 텍스트뷰 계열의 오른쪽에 {@link #iconGood} 그림을 넣어줌
	 * 
	 * @param targetView
	 *            {@link #iconGood} 그림을 넣을 텍스트뷰
	 */
	public static void setGood(TextView forImage) {
		setGood(forImage, null);
	}

	/**
	 * 텍스트뷰 계열의 오른쪽에 {@link #iconBad} 그림을 넣어줌
	 * 
	 * @param targetView
	 *            {@link #iconBad} 그림을 넣을 텍스트뷰
	 */
	public static void setBad(TextView forImage) {
		setBad(forImage, null, 0);
	}

	/**
	 * 텍스트뷰 계열의 오른쪽에 {@link #iconGood} 그림을 넣어줌. 에러표시용 텍스트뷰를 감춤
	 * 
	 * @param forImage
	 *            {@link #iconGood} 그림을 넣을 텍스트뷰
	 * 
	 * @param forText
	 *            에러표시용 텍스트뷰를 감춤
	 * 
	 */
	public static void setGood(TextView forImage, TextView forText) {
		setGood(forImage, forText, 0);
	}

	/**
	 * 텍스트뷰 계열의 오른쪽에 {@link #iconGood} 그림을 넣어줌. 에러표시용 텍스트뷰에 메세지 표시
	 * 
	 * @param forImage
	 *            {@link #iconGood} 그림을 넣을 텍스트뷰
	 * 
	 * @param forText
	 *            에러표시용 텍스트뷰
	 * 
	 * @param stringId
	 *            표시할 문장의 ID
	 */
	public static void setGood(TextView forImage, TextView forText, int stringId) {
		forImage.setCompoundDrawablesWithIntrinsicBounds(null, null, iconGood, null);
		
		if (forText != null) {
			forText.setVisibility(View.INVISIBLE);
			if (stringId != 0)
				forText.setText(stringId);
		}
	}

	/**
	 * 텍스트뷰 계열의 오른쪽에 {@link #iconBad} 그림을 넣어줌. 에러표시용 텍스트뷰에 메세지 표시
	 * 
	 * @param targetView
	 *            {@link #iconBad} 그림을 넣을 텍스트뷰
	 * 
	 * @param forText
	 *            에러표시용 텍스트뷰
	 * 
	 * @param stringId
	 *            표시할 문장의 ID
	 */
	public static void setBad(TextView forImage, TextView forText, int stringId) {
		forImage.setCompoundDrawablesWithIntrinsicBounds(null, null, iconBad, null);
		
		if (forText != null) {
			AnimUtil.startFadeInAnim(forText);
			if (stringId != 0)
				forText.setText(stringId);
		}
	}
}
