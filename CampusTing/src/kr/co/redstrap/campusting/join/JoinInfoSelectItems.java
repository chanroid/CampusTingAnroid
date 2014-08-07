package kr.co.redstrap.campusting.join;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.co.redstrap.campusting.R;

public class JoinInfoSelectItems {

	public static final int LOCAL = 0;
	public static final int CHARACTER = 1;
	public static final int BODY_MALE = 2;
	public static final int BODY_FEMALE = 22;
	public static final int BLOOD_TYPE = 3;
	public static final int RELIGION = 4;
	public static final int DRINK = 5;
	public static final int MAJOR = 7;
	public static final int COUPLECOUNT = 8;

	public static final String[] local = { "서울", "부산", "인천", "광주", "대전", "대구",
			"울산", "경기(일산)", "경기(의정부)", "경기(안양)", "경기(분당)", "경기(수원)", "경기(기타)",
			"강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "해외" };

	public static final String[] characters = { "당당한", "발랄한", "정직한", "솔직한",
			"다정한", "애교있는", "교양있는", "이국적인", "도도한", "치명적인", "착한", "겸손한", "섬세한",
			"낯가리는", "털털한", "열성적인", "낭만적인", "자유로운", "배려깊은", "헌신적인", "지적인",
			"사고적인", "이성적인", "현실적인", "유머있는", "긍정적인", "완벽한", "끈기있는" };

	public static final String[] body = { "강인한 팔뚝", "태평양처럼 넓은 어깨", "쵸콜릿 복근",
			"축구선수같은 허벅지", "귀여운 곰돌이 배", "완벽한 보디빌더 몸매", "모델비율 몸매", "탄탄하고 업된 엉덩이" };

	public static final String[] womanbody = { "이쁜 골반라인", "물이 고일듯한 쇄골",
			"글래머러스한 몸매", "자꾸만 보고싶은 목선", "쫙빠진 일자다리", "돌아보게 만드는 뒷태", "가느다란 허리",
			"CD로 가려질 얼굴" };

	public static final String[] religion = { "종교없음", "천주교", "기독교", "원불교",
			"이슬람교", "기타" };

	public static final String[] bloodTypes = { "O", "A", "B", "AB" }; // 청웅주의

	public static final String[] drink = { "전혀 안마셔요", "어쩔 수 없을 때만", "가끔 마셔요",
			"어느정도 즐겨요", "자주 마셔요" };

	public static final String[] major = { "인문대학", "경영대학", "법과대학", "사범대학",
			"공과대학", "미술대학", "음악대학", "체육대학", "간호대학", "의과대학", "약학대학", "농업대학",
			"생명과학대학", "자연과학대학", "사회과학대학" };

	public static final String[] couple = { "없음", "1~2회", "3~5회", "6회 이상" };

	public static ArrayList<Map<String, Object>> get(int requestCode) {
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		String[] titleArray = null;
		String resNameFormat = "join_blood_nor";
		// 20140806 chanroid 아이콘 리소스는 클릭되는것 까지 있기는 한데 나중에 적용하자... 제발

		Class<?> drawableClass = R.drawable.class;

		switch (requestCode) {
		case LOCAL:
			titleArray = local;
			resNameFormat = "join_place_nor%d";
			break;
		case CHARACTER:
			titleArray = characters;
			resNameFormat = "join_character_nor%d";
			break;
		case BODY_MALE:
			titleArray = body;
			resNameFormat = "join_man_nor%d";
			break;
		case BODY_FEMALE:
			titleArray = womanbody;
			resNameFormat = "join_woman_nor%d";
			break;
		case BLOOD_TYPE:
			titleArray = bloodTypes;

			// 20140806 기본 이름이 혈액형임. format이 안들어가므로 그냥 사용
			for (int i = 1; i < titleArray.length + 1; i++) {
				try {
					int resId = drawableClass.getField(resNameFormat).getInt(
							null);
					addItem(data, resId, titleArray[i - 1], i);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return data; // 추가로 처리하지 않고 바로 빠져나감
		case RELIGION:
			titleArray = religion;
			resNameFormat = "join_faith_nor%d";
			break;
		case DRINK:
			titleArray = drink;
			resNameFormat = "join_alcohol_nor%d";
			break;
		case MAJOR:
			titleArray = major;
			resNameFormat = "join_major_nor%d";
			break;
		case COUPLECOUNT:
			titleArray = couple;
			resNameFormat = "join_love_nor%d";
			break;
		}

		for (int i = 1; i < titleArray.length + 1; i++) {
			try {
				int resId = drawableClass.getField(
						String.format(resNameFormat, i)).getInt(null);
				addItem(data, resId, titleArray[i - 1], i);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return data;
	}

	private static void addItem(ArrayList<Map<String, Object>> data,
			Integer iconRes, String title, Integer index) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("icon", iconRes);
		map.put("title", title);
		map.put("index", index);
		data.add(map);
	}
}
