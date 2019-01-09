package us.dontcareabout.PushAsInput.client.vo;

import com.google.gwt.core.client.JavaScriptObject;

import us.dontcareabout.PushAsInput.shared.RawPush;

public class RawPushJS extends JavaScriptObject implements RawPush {
	protected RawPushJS() {}

	@Override
	public final native int getType() /*-{
		return this.type;
	}-*/;

	@Override
	public final native void setType(String typeString) /*-{
		this.type = typeString;
	}-*/;

	@Override
	public final native String getId() /*-{
		return this.id;
	}-*/;

	@Override
	public final native void setId(String id) /*-{
		this.id = id;
	}-*/;

	@Override
	public final native String getContent() /*-{
		return this.content;
	}-*/;

	@Override
	public final native void setContent(String content) /*-{
		this.content = content;
	}-*/;
}
