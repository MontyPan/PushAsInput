package us.dontcareabout.PushAsInput.shared;

public interface RawPush {

	/**
	 * @return -1 是「噓」、0 是箭頭、1 是「推」
	 */
	int getType();

	/**
	 * 如果無法判別為推或噓就會認定為箭頭
	 */
	void setType(String typeString);

	/**
	 * @return 推文者 ID
	 */
	String getId();

	void setId(String id);

	/**
	 * @return 推文內容
	 */
	String getContent();

	/**
	 * 注意：會略過頭兩個 char（固定出現的半形冒號與空格）
	 */
	void setContent(String content);

}