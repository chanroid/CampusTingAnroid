package kr.co.redstrap.campusting.deprecated;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;

/**
 * 이미지 업로드는 여러모로 통신모듈에 포함시켜서 쓰는게 좋을듯 함
 * 
 * @author rbi_bi_3
 *
 */
public abstract class UploadPicture {

	private String userId;
	private List<String> mList;
	private Activity mContext;

	private UploadPicture() {
		super();
	}

	public UploadPicture(Activity context, String userId, List<String> mList) {
		super();
		this.mContext = context;
		this.userId = userId;
		this.mList = mList;
	}

	public String getmUser() {
		return userId;
	}

	public void setmUser(String userId) {
		this.userId = userId;
	}

	public List<String> getmList() {
		return mList;
	}

	public void setmList(List<String> mList) {
		this.mList = mList;
	}

	public abstract void onTaskFinish();

	/**
	 * 
	 * @param finishActivityAfterTask
	 *            다이얼로그를 띄운 액티비티의 종료를 원할 경우 입력
	 */
	public void uploadPicture() {
		WebUploadTask<Integer> task = null;
		task = new WebUploadTask<Integer>() {
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				onTaskFinish();
			}
		};
		task.executeSerial();
	}

	private abstract class WebUploadTask<Params> extends WebTask<Params, Integer, String> {

		private ProgressDialog progress;

		private final String boundary = "**B**O**U**N**D**A**R**Y**";
		private DataOutputStream dataOutput;

		public WebUploadTask() {
			super("uploadPicture");
		}

		@Override
		protected void onPreExecute() {
			super.put("userId", userId);
			progress = new ProgressDialog(mContext);
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress.setMessage("데이터 전송중");
			progress.show();
		}

		@Override
		protected void onProgressUpdate(Integer... value) {
		}

		@Override
		protected String doInBackground(Params... params) {
			HttpURLConnection con = null;
			try {
				con = conUtil.getConnection(super.queryMap);
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.setRequestProperty("Connection", "Keep-Alive");
				con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

				dataOutput = new DataOutputStream(con.getOutputStream());

				writeImg();

				con.getResponseMessage();
				dataOutput.flush();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					con.disconnect();
				}
				if (dataOutput != null) {
					try {
						dataOutput.close();
					} catch (IOException e) {
						Log.e("IOException", e.toString()); // 스트림 닫기 에러
					}
				}
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
		}

		private void writeImg() throws Exception {
			List<String> pathList = UploadPicture.this.mList;
			int size = pathList.size();
			Log.i("사이즈", "" + size);
			for (int i = 0; i < size; i++) {
				writeBlock(Uri.parse(pathList.get(i)), i);
			}
			writeEndBlock();

		}

		private void writeBoundaryBlock() throws Exception {
			dataOutput.writeBytes("--" + boundary + "\r\n");
		}

		private void writeEndBlock() throws Exception {
			dataOutput.writeBytes("--" + boundary + "--");
		}

		private void writeBlock(Uri path, int order) throws Exception {

			// 최대크기 제한
			int normalMax = 1280;
			Bitmap bm;
			File targetFile;
			FileOutputStream fileOutput;
			byte[] buffer = new byte[9 * 1024]; // 버퍼 : 9kb = 96 x 96
			int bufferLength = 0;

			// ��Ƽ��Ʈ �ۼ��� �ʿ��� ����
			String name = "pic_" + order;
			String fileName = null;
			String type = "image/jpg";

			writeBoundaryBlock(); // �ٿ���� �? (NORMAL)
			fileName = order + ".jpg"; // NORMAL ����, 1����� ����
			dataOutput.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"\r\nContent-Type: " + type + "\r\n\r\n");
			// NORMAL ���� ������ ����
			// NORMAL ���� ������ ����
			// NORMAL ���� ������ ����
			// NORMAL ���� ������ ����
			// NORMAL ���� ������ ����
			// NORMAL ���� ������ ����

			Cursor cursor = mContext.getContentResolver().query(path, null, null, null, null);
			cursor.moveToNext();
			long origId = cursor.getLong(cursor.getColumnIndex(ImageColumns._ID));
			int origWidth = cursor.getInt(cursor.getColumnIndex(ImageColumns.WIDTH));
			int origHeight = cursor.getInt(cursor.getColumnIndex(ImageColumns.HEIGHT));
			String origData = cursor.getString(cursor.getColumnIndex(ImageColumns.DATA));

			if (origWidth == 0 || origHeight == 0) {
				Options opts = new Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(origData, opts);
				origWidth = opts.outWidth;
				origHeight = opts.outHeight;
			}

			cursor.close();

			// 1280�� �������� ��� �� ���Ϸ� ���ø� �� �� �ִ� �ּ��� 2�� ����� ���
			Options opts = new Options();
			int sampleSize = 1;
			int baseLength = Math.max(origWidth, origHeight);
			while (baseLength > normalMax) {
				baseLength = baseLength / 2;
				sampleSize = sampleSize * 2;
			}
			opts.inSampleSize = sampleSize;

			// ���ø��� ��Ʈ�� ��
			bm = BitmapFactory.decodeFile(origData, opts);

			// ���� ��Ʈ���� ĳ������� ����
			File cacheDir = mContext.getCacheDir();
			File targetDir = new File(cacheDir, UploadPicture.this.userId);
			if (!targetDir.exists()) {
				targetDir.mkdirs();
			}
			targetFile = new File(targetDir, fileName);
			if (!targetFile.exists()) {
				targetFile.createNewFile();
			}
			fileOutput = new FileOutputStream(targetFile);
			bm.compress(CompressFormat.JPEG, 100, fileOutput);
			fileOutput.close();

			// ����� NORMAL ���� ���� EXIF ������ �Է�
			ExifInterface origExif = new ExifInterface(origData);
			ExifInterface targetExif = new ExifInterface(targetFile.getAbsolutePath());
			try {
				targetExif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, origExif.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
				targetExif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, origExif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
				targetExif.saveAttributes();
			} catch (Exception e) {
				Log.i("EXIF", e.toString());
			}

			// �۾� �Ϸ�� NORMAL ������ ������ ���
			FileInputStream fileInput = new FileInputStream(targetFile);
			while ((bufferLength = fileInput.read(buffer)) != -1) {
				dataOutput.write(buffer, 0, bufferLength);
			}
			fileInput.close();

			// ��Ƽ��Ʈ ������
			dataOutput.writeBytes("\r\n");

			// MINI ���� ������ ����
			// MINI ���� ������ ����
			// MINI ���� ������ ����
			// MINI ���� ������ ����
			// MINI ���� ������ ����
			// MINI ���� ������ ����
			writeBoundaryBlock(); // �ٿ���� �? (MINI)
			fileName = order + "_mini.jpg"; // MINI ����
			dataOutput.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"\r\nContent-Type: " + type + "\r\n\r\n");

			bm = Thumbnails.getThumbnail(mContext.getContentResolver(), origId, Thumbnails.MINI_KIND, null);
			boolean miniBool = bm.compress(CompressFormat.JPEG, 100, dataOutput);
			Log.i("miniBool", "" + miniBool);

			// ��Ƽ��Ʈ ������
			dataOutput.writeBytes("\r\n");

			// MICRO ���� ������ ����
			// MICRO ���� ������ ����
			// MICRO ���� ������ ����
			// MICRO ���� ������ ����
			// MICRO ���� ������ ����
			// MICRO ���� ������ ����
			writeBoundaryBlock(); // �ٿ���� �? (MICRO)
			fileName = order + "_micro.jpg"; // MICRO ����
			dataOutput.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"\r\nContent-Type: " + type + "\r\n\r\n");

			bm = Thumbnails.getThumbnail(mContext.getContentResolver(), origId, Thumbnails.MICRO_KIND, null);
			boolean microBool = bm.compress(CompressFormat.JPEG, 100, dataOutput);
			Log.i("microBool", "" + microBool);

			// ��Ƽ��Ʈ ������
			dataOutput.writeBytes("\r\n");

		}

	}
}
