package kr.co.redstrap.campusting.deprecated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import android.util.Log;

public abstract class WebOneCharTask<Params, Progress> extends WebTask<Params, Progress, Character> {

	private WebOneCharTask() {
		super();
	}

	/**
	 * 
	 * @param request
	 *            RequestMapping에 사용될 값
	 */
	public WebOneCharTask(String request) {
		super(request);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Character doInBackground(Params... params) {
		HttpURLConnection con = null;
		BufferedReader urlReader = null;
		Character oneChar = null;
		try {
			con = conUtil.getConnection(queryMap);
			urlReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			oneChar = (char) urlReader.read();
			con.disconnect();
		} catch (Exception e) {
			Log.e("에러", e.toString());
		} finally {
			if (con != null) {
				con.disconnect();
			}
			if (urlReader != null) {
				try {
					urlReader.close();
				} catch (IOException e) {
					Log.e("IOException", e.toString()); // 스트림 닫기 에러
				}
			}
		}
		return oneChar;
	}

}