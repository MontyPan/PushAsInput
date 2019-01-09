package us.dontcareabout.PushAsInput.client.data;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.PushAsInput.client.data.LocationChangeEvent.LocationChangeHandler;

public class LocationChangeEvent extends GwtEvent<LocationChangeHandler> {
	public static final Type<LocationChangeHandler> TYPE = new Type<LocationChangeHandler>();

	public final String location;
	public final boolean enable;

	public LocationChangeEvent(String location, boolean enable) {
		this.location = location;
		this.enable = enable;
	}

	@Override
	public Type<LocationChangeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LocationChangeHandler handler) {
		handler.onLocationChange(this);
	}

	public interface LocationChangeHandler extends EventHandler{
		public void onLocationChange(LocationChangeEvent event);
	}

}
