package kr.co.redstrap.campusting.util;

import java.util.Calendar;
import java.util.Locale;

public class DayUtil {
	
	public static boolean isDay() {
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		
		return (hour >= 6 && hour < 18);
	}

}
