package kr.co.redstrap.campusting.common;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginInfo {

	private SharedPreferences mPref;

	private static LoginInfo instance;

	public static LoginInfo getInstance(Context ctx) {
		if (instance == null) {
			instance = new LoginInfo(ctx);
		}
		return instance;
	}

	private LoginInfo(Context ctx) {
		mPref = ctx.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
	}

	public void setAutoLogin(boolean flag) {
		mPref.edit().putBoolean("autoLogin", flag).commit();
	}

	public boolean isAutoLogin() {
		// TODO Auto-generated method stub
		return mPref.getBoolean("autoLogin", false);
	}

	public String getUserId() {
		return mPref.getString("userId", "");
	}

	public void setUserId(String id) {
		mPref.edit().putString("userId", id).commit();
	}

	public String getUserPw() {
		return mPref.getString("userPw", "");
	}

	public void setUserPw(String pw) {
		mPref.edit().putString("userPw", pw).commit();
	}

	public void setAcEmail(String email) {
		mPref.edit().putString("univEmail", email).commit();
	}

	public void setRegDate(String string) {
		mPref.edit().putString("regDate", string).commit();
	}

	public void setPhotoCount(int count) {
		mPref.edit().putInt("photoCount", count).commit();
	}

	public void setUnivNum(int num) {
		mPref.edit().putInt("univNum", num).commit();
	}

	public void setIsAccept(boolean accept) {
		mPref.edit().putBoolean("isAccept", accept).commit();
	}

	public void setUserNum(int num) {
		mPref.edit().putInt("userNum", num).commit();
	}

	public void setNickName(String nickName) {
		mPref.edit().putString("nickName", nickName).commit();
	}

	public void setMajorNum(int major) {
		mPref.edit().putInt("major", major).commit();
	}

}
