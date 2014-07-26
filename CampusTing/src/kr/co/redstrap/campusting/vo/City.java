package kr.co.redstrap.campusting.vo;

import java.io.Serializable;

public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1220000623183345992L;

	private String cityId;
	private String cityName;

	public City() {
		// TODO Auto-generated constructor stub
	}

	public City(String cityId, String cityName) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", cityName=" + cityName + "]";
	}

}
