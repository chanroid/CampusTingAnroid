package kr.co.redstrap.campusting.common;

public class AppInfo {
	private String recentVersion;
	
	private AppInfo() {
		
	}
	
	private static AppInfo instance;
	
	public synchronized static AppInfo getInstance() {
		if (instance == null)
			instance = new AppInfo();
		return instance;
	}
	
	public void setRecentVersion(String recentVersion) {
		this.recentVersion = recentVersion;
	}
	
	public String getRecentVersion() {
		return recentVersion;
	}
}
