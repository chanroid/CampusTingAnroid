package kr.co.redstrap.campusting.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

public class PwInputFilter implements InputFilter {

	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		Log.i("" + source.length(), source + " // " + start + " // " + end + " // " + dest + " // " + dstart + " // " + dend);
		if (source.length() != 1) {
			return "";
		} else {
			if (source.charAt(0) < 33 || 175 < source.charAt(0)) {
				return "";
			}
		}

		return null;
	}

}
