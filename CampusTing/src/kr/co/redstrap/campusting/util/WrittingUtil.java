package kr.co.redstrap.campusting.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.util.Log;

/**
 * 
 * @author bit
 */
public class WrittingUtil {

	private static WrittingUtil writtingUtil;

	private Context mContext;

	private WrittingUtil() {
	}

	private WrittingUtil(Context context) {
		super();
		this.mContext = context;
	}

	public static WrittingUtil getInstance(Context context) {
		if (writtingUtil == null) {
			synchronized (WrittingUtil.class) {
				if (writtingUtil == null) {
					writtingUtil = new WrittingUtil(context);
				}
			}
		}
		return writtingUtil;
	}

	/**
	 * 
	 * key, value
	 * 
	 * @param key
	 * 		저장할 파일 이름
	 *            
	 * @param value
	 * 		해당 파일에 저장할 객체
	 *            
	 */
	public void saveObject(String key, Object value) {
		Log.w("saveObject", "객체 파일로 저장 : " + key);
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = mContext.openFileOutput(key, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(value);
			oos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * key
	 * 
	 * @param key
	 *            저장된 파일 이름
	 * @return 해당 파일에 저장된 직렬화 객체
	 */
	public Object loadObject(String key) {
		Log.w("loadObject", "파일에서 객체 로드 : " + key);
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Object object = null;
		try {
			fis = mContext.openFileInput(key);
			ois = new ObjectInputStream(fis);
			object = ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return object;
	}

	/**
	 * 
	 * 해당 이름의 파일을 삭제
	 * 
	 * @param key
	 *            삭제할 파일명
	 * @return 삭제여부
	 */
	public boolean deleteObject(String key) {
		Log.w("deleteObject", "��ü ����" + key);
		return mContext.deleteFile(key);
	}

}
