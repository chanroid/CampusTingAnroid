package kr.co.redstrap.campusting.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 상속받으면 추상메서드 안에서 다운로드 호출
 * 
 * @author rbi_bi_3
 *
 */
public abstract class AbsCTFileDownloadSyncTask extends AbsCTSyncTask<String, Void> {

	/**
	 * 실행하면 자동으로 progress 콜백 들어옴
	 * 
	 * @param downloadUrl 서버에 있는 파일 url
	 * @param downloadPath 로컬에 저장할 경로
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void download(String downloadUrl, String downloadPath) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        URL url;
        URLConnection conn;
        try {
            // 서버의 DB 경로로 URL 객체를 생성한다.
            url = new URL(downloadUrl);
            // 해당 URL로 연결한다.
            conn = url.openConnection();
            // 서버에 있는 DB 파일의 용량을 받아온다.
            int lengthOnServer = conn.getContentLength();
            if (lengthOnServer > 0) {
                // 용량이 0보다 크면 제대로 연결되었다고 판단하고 정상 진행한다.
            	publishProgress(0);
            } else {
                // 용량이 0보다 크지 않을 경우 오류임을 표시한다.
                return;
            }
            // 다운로드 받은 파일을 시스템에 저장하기 위해 InputStream을 얻는다.
            is = conn.getInputStream();
            
            // DB 파일을 저장할 경로로 File 객체를 생성한다. (폴더 경로만)
            File target = new File(downloadPath);
            if (!target.exists()) {
            	target.mkdir();
            } else {
                target.delete();
            }
            target.createNewFile();
            
            fos = new FileOutputStream(target);
            bos = new BufferedOutputStream(fos);
            int bufferLength = 0;
            // 다운로드가 진행되는동안 다운로드된 크기를 축적하는 변수
            int totalLength = 0;
            byte[] buffer = new byte[1024];
            while((bufferLength = is.read(buffer)) > 0) {
                // bos.write()로 파일을 쓸 수 있다.
                bos.write(buffer, 0, bufferLength);
                //총 받은 양을 기록한다.
                totalLength += bufferLength;
                int progress = (lengthOnServer / totalLength) * 100;
                publishProgress(progress);
            }

        	publishProgress(100);
        } finally {
            try {
                if (bos != null) bos.close();
                if (fos != null) fos.close();
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
