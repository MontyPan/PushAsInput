package us.dontcareabout.PushAsInput.shared;

import java.io.Serializable;

public class RawPushImpl implements Serializable, RawPush {
	private static final long serialVersionUID = 1L;

	public static final String PUSH = "推";
	public static final String BOO = "噓";
	public static final String ARROW = "→";

	int type;
	String id;
	String content;
	//不考慮日期時間，因為原始資料就沒有年份，基本上沒啥意義

	@Override
	public int getType() {
		return type;
	}

	@Override
	public void setType(String typeString) {
		typeString = typeString.trim();
		if (typeString.equals(PUSH)) {
			type = 1;
		} else if (typeString.equals(BOO)) {
			type = -1;
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
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
