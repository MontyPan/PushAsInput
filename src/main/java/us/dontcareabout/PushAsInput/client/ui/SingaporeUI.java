package us.dontcareabout.PushAsInput.client.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;

import us.dontcareabout.PushAsInput.client.PushAsInputEP;
import us.dontcareabout.PushAsInput.client.component.LocationList;
import us.dontcareabout.PushAsInput.client.component.SingaporeGrid;
import us.dontcareabout.PushAsInput.client.data.DataReadyEvent;
import us.dontcareabout.PushAsInput.client.data.DataReadyEvent.DataReadyHandler;
import us.dontcareabout.PushAsInput.client.data.LocationChangeEvent;
import us.dontcareabout.PushAsInput.client.data.LocationChangeEvent.LocationChangeHandler;
import us.dontcareabout.PushAsInput.shared.ContentParse;
import us.dontcareabout.PushAsInput.shared.RawPush;
import us.dontcareabout.PushAsInput.shared.Singapore;

public class SingaporeUI extends Composite {
	private static SingaporeUIUiBinder uiBinder = GWT.create(SingaporeUIUiBinder.class);
	interface SingaporeUIUiBinder extends UiBinder<Widget, SingaporeUI> {}

	private static final SimpleEventBus eventBus = new SimpleEventBus();

	@UiField SingaporeGrid grid;
	@UiField LocationList locationList;

	private ArrayList<Singapore> data = new ArrayList<>();

	public SingaporeUI() {
		initWidget(uiBinder.createAndBindUi(this));
		PushAsInputEP.addDataReady(new DataReadyHandler() {
			@Override
			public void onDataReady(DataReadyEvent event) {
				process(event.data);
			}
		});
	}

	private void process(ArrayList<RawPush> rawData) {
		final Date now = new Date();
		data.clear();
		HashMap<String, Singapore> map = new HashMap<>();

		for (RawPush rp : rawData) {
			Singapore tupple = ContentParse.singapore(rp.getContent());

			//parse 錯誤跳過
			if (tupple == null) { continue; }
			//時間超過也跳過
			if (tupple.getDeadline() != null && tupple.getDeadline().getTime() < now.getTime()) { continue; }

			tupple.setUserId(rp.getId());
			Singapore old = map.put(tupple.getUserId(), tupple);

			if (old != null) { data.remove(old); }

			data.add(tupple);
		}

		grid.setData(data);
		locationList.setData(data);
	}

	public static void locationChange(String location, boolean enable) {
		eventBus.fireEvent(new LocationChangeEvent(location, enable));
	}

	public static HandlerRegistration addLocationChange(LocationChangeHandler handler) {
		return eventBus.addHandler(LocationChangeEvent.TYPE, handler);
	}
}
