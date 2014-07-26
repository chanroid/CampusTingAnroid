package kr.co.redstrap.campusting.util;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.R;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

/**
 * 어플리케이션 실행시 초기화 시켜주는 부분을 넣어야 정상작동함.
 * 
 * @link {@link MainApp#initAnimUtil(android.content.Context)}
 * 
 * @author play
 * 
 */
public class AnimUtil {

	public static Context appContext;
	public static final long DEFAULT_ANIM_DURATION = 500;

	private AnimUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 페이드인 애니메이션
	 * 
	 * @param view
	 *            애니메이션을 적용할 뷰
	 * @param millis
	 *            1번째는 Duration, 2번째는 DelayTime
	 * 
	 */
	public static void startFadeInAnim(final View view, long... millis) {
		Animation anim = new AlphaAnimation(0, 1);
		switch (millis.length) {

		case 1: // 지정된 Duration 설정
			anim.setDuration(millis[0]);
			startInAnim(anim, view);
			break;
		case 2:// 지정된 Duration 및 Delay 설정
			anim.setDuration(millis[0]);
			anim.setStartOffset(millis[1]);
			startInAnim(anim, view);
			break;

		default: // 기본 Duration 설정
			anim.setDuration(DEFAULT_ANIM_DURATION);
			startInAnim(anim, view);
			break;
		}
	}

	/**
	 * 페이드아웃 애니메이션
	 * 
	 * @param view
	 *            애니메이션을 적용할 뷰
	 * @param millis
	 *            1번째는 Duration, 2번째는 DelayTime
	 * 
	 */
	public static void startFadeOutAnim(final View view, long... millis) {
		Animation anim = new AlphaAnimation(1, 0);
		switch (millis.length) {

		case 1: // 지정된 Duration 설정
			anim.setDuration(millis[0]);
			startOutAnim(anim, view);
			break;
		case 2:// 지정된 Duration 및 Delay 설정
			anim.setDuration(millis[0]);
			anim.setStartOffset(millis[1]);
			startOutAnim(anim, view);
			break;

		default: // 기본 Duration 설정
			anim.setDuration(DEFAULT_ANIM_DURATION);
			startOutAnim(anim, view);
			break;
		}

	}

	/**
	 * 그냥 시작
	 * 
	 * @param anim
	 * @param view
	 */
	private static void startAnim(Animation anim, final View view) {
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				view.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});
		view.startAnimation(anim);
	}

	/**
	 * 안 보이는 뷰를 보이게 만들고 애니메이션 시작
	 * 
	 * @param anim
	 * @param view
	 */
	private static void startInAnim(Animation anim, final View view) {
		if (view.getVisibility() != View.VISIBLE) {
			anim.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					view.setVisibility(View.VISIBLE);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
				}
			});
			view.startAnimation(anim);
		}
	}

	/**
	 * 보이는 뷰를 애니메이션 끝날 때 사라지게 함
	 * 
	 * @param anim
	 * @param view
	 */
	private static void startOutAnim(Animation anim, final View view) {
		if (view.getVisibility() == View.VISIBLE) {
			anim.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					view.setVisibility(View.INVISIBLE);
				}
			});
			view.startAnimation(anim);
		}
	}
}
