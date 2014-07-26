package kr.co.redstrap.campusting.deprecated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.json.JSONObject;

import android.util.Log;

public abstract class WebOneJsonTask<Params, Progress> extends WebTask<Params, Progress, JSONObject> {

	private WebOneJsonTask() {
		super();
	}

	/**
	 * 
	 * @param request
	 *            RequestMapping에 사용될 값
	 */
	public WebOneJsonTask(String request) {
		super(request);
	}

	@Override
	protected JSONObject doInBackground(Params... params) {
		HttpURLConnection con = null;
		BufferedReader urlReader = null;
		JSONObject jsonObject = null;
		try {
			con = conUtil.getConnection(queryMap);
			urlReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			jsonObject = new JSONObject(urlReader.readLine());
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
		return jsonObject;
	}

}