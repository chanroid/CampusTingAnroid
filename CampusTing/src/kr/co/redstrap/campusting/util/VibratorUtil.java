package kr.co.redstrap.campusting.util;

import kr.co.redstrap.campusting.MainApp;
import android.os.Vibrator;

/**
 * 어플리케이션 실행시 초기화 시켜주는 부분을 넣어야 정상작동함.
 * 
 * @link {@link MainApp#initVibratorUtil(android.content.Context)}
 * 
 * @author play
 * 
 */
public class VibratorUtil {

	public static Vibrator vibrator;
	private static final long VIBE_TIME = 32;

	private VibratorUtil() {
	}

	/**
	 * 32밀리초만큼 진동이 울립니다.
	 * 
	 * @see #VIBE_TIME
	 */
	public static void vibe() {
		vibrator.vibrate(VIBE_TIME);
	}

	/**
	 * 입력한 시간(밀리초)만큼 진동이 울립니다.
	 * 
	 * @param vibeTime
	 *            진동시간(밀리초)
	 */
	public static void vibe(long vibeTime) {
		vibrator.vibrate(vibeTime);
	}

}
