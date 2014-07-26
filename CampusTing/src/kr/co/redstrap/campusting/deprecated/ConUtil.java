package kr.co.redstrap.campusting.deprecated;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import kr.co.redstrap.campusting.constant.CampusTingConstant;
import kr.co.redstrap.campusting.util.web.UrlUtil;
import android.util.Log;

/**
 * HttpURLConnection을 얻기 위한 클래스
 * 
 * @author play
 * 
 */
public class ConUtil {

	private String request;

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	private ConUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param request
	 *            RequestMapping에 사용될 값
	 */
	public ConUtil(String request) {
		this.request = request;
	}

	/**
	 * 
	 * @param queryMap
	 * 
	 * @return
	 */
	/**
	 * 
	 * @param queryMap
	 *            요청을 보낼 쿼리
	 * @return {@link HttpURLConnection}
	 * @throws Exception
	 *             호출한 클래스로 모든 에러 처리를 위임
	 */
	public HttpURLConnection getConnection(Map<String, String>... queryMap) throws Exception {
		URL url = null;
		HttpURLConnection con = null;
		StringBuilder fullPath = new StringBuilder(UrlUtil.campusTingWebUrl + this.request + "?conType=" + CampusTingConstant.Parameter.CON_TYPE_ANDROID);
		if (queryMap == null || queryMap.length == 0) {
			url = new URL(fullPath.toString());
		} else {
			Set<String> keySet = queryMap[0].keySet();
			for (String key : keySet) {
				fullPath.append("&").append(key).append("=").append(queryMap[0].get(key));
			}
			Log.i("웹주소", fullPath.toString());
			url = new URL(fullPath.toString());
		}
		Log.i("웹주소", url.toString());
		con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(2500);
		con.setUseCaches(false);

		return con;
	}
}
