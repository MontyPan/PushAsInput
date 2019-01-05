package us.dontcareabout.PushAsInput.shared;

import java.io.Serializable;

public class RawPush implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String PUSH = "推";
	public static final String BOO = "噓";
	public static final String ARROW = "→";

	int type;
	String id;
	String content;
	//不考慮日期時間，因為原始資料就沒有年份，基本上沒啥意義

	/**
	 * @return -1 是「噓」、0 是箭頭、1 是「推」
	 */
	public int getType() {
		return type;
	}

	/**
	 * 如果無法判別為推或噓就會認定為箭頭
	 */
	public void setType(String typeString) {
		typeString = typeString.trim();
		if (typeString.equals(PUSH)) {
			type = 1;
		} else if (typeString.equals(BOO)) {
			type = -1;
		}
	}

	/**
	 * @return 推文者 ID
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return 推文內容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 注意：會略過頭兩個 char（固定出現的半形冒號與空格）
	 */
	public void setContent(String content) {
		this.content = content.substring(2);
	}

	@Override
	public String toString() {
		//還原回 PTT 顯示的格式
		StringBuffer result = new StringBuffer();
		switch(type) {
		case -1:
			result.append(BOO); break;
		case 0:
			result.append(ARROW); break;
		case 1:
			result.append(PUSH); break;
		}
		return result.append(" ").append(id).append(": ").append(content).toString();
	}
}
