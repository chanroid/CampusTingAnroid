package kr.co.redstrap.campusting.vo;

import java.io.Serializable;

public class School implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1418143544465629461L;

	private String schoolId;
	private String region;
	private String schoolName;
	private String domain;

	public School() {
		// TODO Auto-generated constructor stub
	}

	public School(String schoolId, String region, String schoolName, String domain) {
		super();
		this.schoolId = schoolId;
		this.region = region;
		this.schoolName = schoolName;
		this.domain = domain;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "School [schoolId=" + schoolId + ", region=" + region + ", schoolName=" + schoolName + ", domain=" + domain + "]";
	}

}
