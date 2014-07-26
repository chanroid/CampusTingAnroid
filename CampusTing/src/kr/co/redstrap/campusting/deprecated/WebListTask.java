package kr.co.redstrap.campusting.deprecated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.util.Log;

/**
 * 생성시 RequestMapping에 사용될 String값을 입력합니다.
 * 
 * {@link #doInBackground(Object...)} 단계에서 List<JSONObject> 형식의 결과물을 {@link #onPostExecute(Object)}로 전달한다.
 * 
 * 사용자는 {@link #onPostExecute(Object)}를 오버라이딩하여 처리하고 싶은 부분을 처리하면 된다.
 * 
 * @author play
 * 
 * @param <Params>
 * @param <Progress>
 */
public abstract class WebListTask<Params, Progress> extends WebTask<Params, Progress, List<JSONObject>> {

	/**
	 * 사용 불가
	 */
	private WebListTask() {
		super();
	}

	/**
	 * 
	 * @param request
	 *            RequestMapping에 사용될 값
	 */
	public WebListTask(String request) {
		super(request);
	}

	@Override
	protected List<JSONObject> doInBackground(Params... params) {
		HttpURLConnection con = null;
		BufferedReader urlReader = null;
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			con = conUtil.getConnection(queryMap);
			urlReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = urlReader.readLine()) != null) {
				list.add(new JSONObject(line));
			}
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
		return list;
	}
}