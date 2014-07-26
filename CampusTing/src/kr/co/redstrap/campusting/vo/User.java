package kr.co.redstrap.campusting.vo;

import java.io.Serializable;

/**
 * 
 * 20140701 chanroid <br>
 * 아직 멤버의 모든 변수들이 무엇을 의미하는지, <br>
 * 어떠한 값이 들어가야되는지 모두 알 수 없음. <br>
 * 
 * @author rbi_bi_3
 *
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -150307354420585369L;

	private String userId;
	private String schoolId;
	private String deptId;
	private String studentNum;
	private String userLoginId;
	private String loginTypeId;
	private String pw;
	private String mobileId;
	private String mobileType;
	private String cpNum;
	private String userName;
	private String gender;
	private String birth;
	private String acEmail;
	private String bodyTypeId;
	private String height;
	private String joinDate;
	private String lastConnection;
	private String pictureNum;
	private String mainPicture;
	private String personalityId1;
	private String personalityId2;
	private String personalityId3;
	private String idealType;
	private String appeal;
	private String confirm;
	private String judge;
	private String cityId;
	private String termsDate;

	public User() {
	}

	public User(String userId, String schoolId, String deptId, String studentNum, String userLoginId, String loginTypeId, String pw, String mobileId, String mobileType,
			String cpNum, String userName, String gender, String birth, String acEmail, String bodyTypeId, String height, String joinDate, String lastConnection,
			String pictureNum, String mainPicture, String personalityId1, String personalityId2, String personalityId3, String idealType, String appeal, String confirm,
			String judge, String cityId, String termsDate) {
		super();
		this.userId = userId;
		this.schoolId = schoolId;
		this.deptId = deptId;
		this.studentNum = studentNum;
		this.userLoginId = userLoginId;
		this.loginTypeId = loginTypeId;
		this.pw = pw;
		this.mobileId = mobileId;
		this.mobileType = mobileType;
		this.cpNum = cpNum;
		this.userName = userName;
		this.gender = gender;
		this.birth = birth;
		this.acEmail = acEmail;
		this.bodyTypeId = bodyTypeId;
		this.height = height;
		this.joinDate = joinDate;
		this.lastConnection = lastConnection;
		this.pictureNum = pictureNum;
		this.mainPicture = mainPicture;
		this.personalityId1 = personalityId1;
		this.personalityId2 = personalityId2;
		this.personalityId3 = personalityId3;
		this.idealType = idealType;
		this.appeal = appeal;
		this.confirm = confirm;
		this.judge = judge;
		this.cityId = cityId;
		this.termsDate = termsDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getLoginTypeId() {
		return loginTypeId;
	}

	public void setLoginTypeId(String loginTypeId) {
		this.loginTypeId = loginTypeId;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getMobileId() {
		return mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}

	public String getMobileType() {
		return mobileType;
	}

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	public String getCpNum() {
		return cpNum;
	}

	public void setCpNum(String cpNum) {
		this.cpNum = cpNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getAcEmail() {
		return acEmail;
	}

	public void setAcEmail(String acEmail) {
		this.acEmail = acEmail;
	}

	public String getBodyTypeId() {
		return bodyTypeId;
	}

	public void setBodyTypeId(String bodyTypeId) {
		this.bodyTypeId = bodyTypeId;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getLastConnection() {
		return lastConnection;
	}

	public void setLastConnection(String lastConnection) {
		this.lastConnection = lastConnection;
	}

	public String getPictureNum() {
		return pictureNum;
	}

	public void setPictureNum(String pictureNum) {
		this.pictureNum = pictureNum;
	}

	public String getMainPicture() {
		return mainPicture;
	}

	public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}

	public String getPersonalityId1() {
		return personalityId1;
	}

	public void setPersonalityId1(String personalityId1) {
		this.personalityId1 = personalityId1;
	}

	public String getPersonalityId2() {
		return personalityId2;
	}

	public void setPersonalityId2(String personalityId2) {
		this.personalityId2 = personalityId2;
	}

	public String getPersonalityId3() {
		return personalityId3;
	}

	public void setPersonalityId3(String personalityId3) {
		this.personalityId3 = personalityId3;
	}

	public String getIdealType() {
		return idealType;
	}

	public void setIdealType(String idealType) {
		this.idealType = idealType;
	}

	public String getAppeal() {
		return appeal;
	}

	public void setAppeal(String appeal) {
		this.appeal = appeal;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTermsDate() {
		return termsDate;
	}

	public void setTermsDate(String termsDate) {
		this.termsDate = termsDate;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", schoolId=" + schoolId + ", deptId=" + deptId + ", studentNum=" + studentNum + ", userLoginId=" + userLoginId + ", loginTypeId="
				+ loginTypeId + ", pw=" + pw + ", mobileId=" + mobileId + ", mobileType=" + mobileType + ", cpNum=" + cpNum + ", userName=" + userName + ", gender=" + gender
				+ ", birth=" + birth + ", acEmail=" + acEmail + ", bodyTypeId=" + bodyTypeId + ", height=" + height + ", joinDate=" + joinDate + ", lastConnection="
				+ lastConnection + ", pictureNum=" + pictureNum + ", mainPicture=" + mainPicture + ", personalityId1=" + personalityId1 + ", personalityId2=" + personalityId2
				+ ", personalityId3=" + personalityId3 + ", idealType=" + idealType + ", appeal=" + appeal + ", confirm=" + confirm + ", judge=" + judge + ", cityId=" + cityId
				+ ", termsDate=" + termsDate + "]";
	}

}
