package kr.co.redstrap.campusting.common;

import kr.co.redstrap.campusting.MainApp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class ErrorProcessor {
	
	/**
	 * 그냥 alert 띄워주는거 뿐..
	 * 
	 * @param result
	 * @param click
	 */
	public static void alert(Context ctx, ErrorResult result, DialogInterface.OnClickListener click) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
		dialog.setTitle("이런!");
		dialog.setMessage(result.message);
		dialog.setCancelable(false);
		dialog.setPositiveButton("확인", click);
		dialog.show();
	}
	
	/**
	 * 
	 * 그냥 toast 띄워주는거 뿐..
	 * 
	 * @param result
	 */
	public static void toast(ErrorResult result) {
		Toast.makeText(MainApp.appContext, result.message, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * json 결과가 에러인지 판별
	 * 
	 * @param json
	 * @return
	 */
	public static boolean isError(JSONObject json) {
		try {
			return json.getInt("errCode") != 200;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
