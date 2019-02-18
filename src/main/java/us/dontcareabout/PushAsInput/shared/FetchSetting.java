package us.dontcareabout.PushAsInput.shared;

import java.io.Serializable;

/**
 * 紀錄 ptt 網址（{@link #url}）以及抓完後存檔檔名（{@link #fileName}）的 vo。
 */
public class FetchSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	private String url;
	private String fileName;

	FetchSetting() {}	//預留給 GWT RPC

	public FetchSetting(String url, String fileName) {
		this.url = url;
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
