package kr.co.redstrap.campusting.constant;

public interface CampusTingConstant {
	
	// 네트워크 요청시 인자값
	public static final String ERROR_CODE = "errCode";
	public static final String ERROR_MESSAGE = "errMsg";
	
	public static final String USER_NUM = "userNum";
	public static final String TARGET_USER_NUM = "targetUserNum";
	public static final String TING_NUM = "tingNum";
	public static final String IS_ACCEPT_TING = "isAcceptTing";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String DISTANCE = "distance";
	public static final String BUILDING = "building";
	public static final String RECEIPT = "receipt";
	public static final String CHARGE_POINT = "chargePoint";
	public static final String TOTAL_POINT = "totalPoint";
	public static final String DEVICE = "device";
	public static final String PUSH_KEY = "pushKey";
	public static final String MESSAGE = "message";
	public static final String MESSAGE_TYPE = "msgType";
	public static final String PHOTO = "photo";
	public static final String GEOPOINT = "geopoint";
	public static final String BLOCK_REASON = "blockReason";
	public static final String BLOCK_REASON_MESSAGE = "blockReasonMsg";
	public static final String TING_REQUEST_TYPE = "tingRequestType";
	public static final String MATCHING_LIST = "matchingList";
	public static final String MAIN_DOMAIN = "mainDomain";
	public static final String DB_VERSION = "dbVer";
	public static final String TIME = "time";
	public static final String MESSAGE_KIND = "msgKind";
	public static final String ACTION = "action";
	public static final String ALERT_INFO = "alertInfo";
	public static final String LIST = "list";
	public static final String IS_CHATING = "isChating";
	public static final String IS_DELETED_CHATING = "isDeletedChating";
	public static final String CHARMING_TYPE = "charmingType";
	public static final String CHARMING_POINT = "charmingPoint";
	public static final String REPORT_TYPE = "reportType";
	public static final String REPORT_POINT = "reportPoint";
	public static final String USER_ID = "userId";
	public static final String USER_PW = "userPw";
	public static final String ACCEPT_TYPE = "acceptType";
	public static final String ACCEPT_NUM = "acceptNum";
	public static final String ACCEPT_VALUE = "acceptValue";
	public static final String POINT = "point";
	
	public static final String UNIV_EMAIL = "univMail";
	public static final String REGISTER_DATE = "regDate";
	public static final String PHOTO_COUNT = "photoCount";
	public static final String IS_ACCEPT = "isAccept";
	public static final String UNIV_NUM = "univNum";
	public static final String MAJOR_NUM = "majorNum";
	public static final String NICKNAME = "nickName";
	
	public static final String DATE = "date";
	public static final String URL = "url";
	public static final String GENDER = "gender";
	public static final String BIRTH = "birthday";
	public static final String PROMO_CODE = "promoCode";
	public static final String LOCAL = "local";
	public static final String CHARACTER = "character";
	public static final String BODY = "body";
	public static final String HEIGHT = "height";
	public static final String BLOOD_TYPE = "bloodType";
	public static final String RELIGION = "religion";
	public static final String SMOKE = "smoke";
	public static final String DRINK = "drink";
	public static final String COUPLE_COUNT = "coupleCount";
	public static final String JOB = "job";
	public static final String MAJOR = "major";
	public static final String PROFILE_PHOTO = "profilePhoto";
	public static final String UNIV_CARD_PHOTO = "univcardPhoto";
	public static final String SIMPLE_INTRODUCE = "simpleIntro";
	public static final String HOBBY = "hobby";
	public static final String IDEAL_TYPE = "idealType";
	public static final String DESC = "desc";
	public static final String IS_UNREGISTER = "isUnregister";
	public static final String REASON = "reason";
	public static final String UNIV_CARD_TYPE = "univcardType";
	
	public class Building {
		public static final int MAIN = 0;
		public static final int GYM = 1;
		public static final int CAFE = 2;
		public static final int LIBRARY = 3;
		public static final int DOMITORY = 4;
		public static final int CIRCLEROOM = 5;
		public static final int HOF = 6;
		public static final int CLUB = 7;
	}
	
	/**
	 * DB의 로그인 타입과 연동된 값을 가짐
	 * 
	 * @author play
	 * 
	 */
	public class LoginType {
		public static final String FACEBOOK = "1";
		public static final String NAVER = "2";
		public static final String CAMPUSTING = "3";
	}

	/**
	 * Intent에서 사용할 리퀘스트 코드
	 * 
	 * @author play
	 * 
	 */
	public class RequestCodes {
		public static final int INTRO_REQUEST = 10;
		public static final int PICK_CAMERA = 11;
		public static final int PICK_GALLERY = 12;
		public static final int CROP_PICTURE = 13;
		public static final int PHOTO_VIEW = 14;
		public static final int BEGINNER = 15;
		public static final int SETTING = 16;
	}

	public class Parameter {
		public static final String CON_TYPE_WEB = "0";
		public static final String CON_TYPE_ANDROID = "1";
		public static final String CON_TYPE_IOS = "2";
	}

}
