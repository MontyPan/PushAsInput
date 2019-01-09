package us.dontcareabout.PushAsInput.client.data;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.PushAsInput.client.data.DataReadyEvent.DataReadyHandler;
import us.dontcareabout.PushAsInput.shared.RawPush;

public class DataReadyEvent extends GwtEvent<DataReadyHandler> {
	public static final Type<DataReadyHandler> TYPE = new Type<DataReadyHandler>();
	public final ArrayList<RawPush> data;

	public DataReadyEvent(ArrayList<RawPush> data) {
		this.data = data;
	}

	@Override
	public Type<DataReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DataReadyHandler handler) {
		handler.onDataReady(this);
	}

	public interface DataReadyHandler extends EventHandler{
		public void onDataReady(DataReadyEvent event);
	}
}
