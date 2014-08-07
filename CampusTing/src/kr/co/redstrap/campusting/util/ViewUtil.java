package kr.co.redstrap.campusting.util;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.R;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewUtil {

	private final static Drawable iconGood = MainApp.appContext.getResources()
			.getDrawable(R.drawable.icon_good);
	private final static Drawable iconBad = MainApp.appContext.getResources()
			.getDrawable(R.drawable.icon_bad);

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
	 * 이메일 '@', '.'의 위치를 통해 간략한 형식 체크
	 * 
	 * @param email
	 *            체크할 이메일
	 * @return 이메일 형식에 맞으면 true를 반환
	 */
	public static boolean isTypeEmail(String email) {
		boolean atCheck = false;
		char[] emailChars = email.toCharArray();
		int emailLength = emailChars.length;
		int target = 0;
		for (target = 0; target < emailLength; target++) {
			if (emailChars[target] == '@') {
				atCheck = true;
				break;
			}
		}

		if (atCheck == false) {
			return false;
		}

		for (int i = target; i < emailLength; i++) {
			if (emailChars[i] == '.') {
				return true;
			}
		}
		return false;
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
	public static void setGood(TextView forImage) {
		forImage.setCompoundDrawablesWithIntrinsicBounds(null, null, iconGood,
				null);
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
	public static void setBad(TextView forImage) {
		forImage.setCompoundDrawablesWithIntrinsicBounds(null, null, iconBad,
				null);
	}
}
