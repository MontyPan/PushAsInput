package us.dontcareabout.PushAsInput.client.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;

import us.dontcareabout.PushAsInput.client.PushAsInputEP;
import us.dontcareabout.PushAsInput.client.component.LocationList;
import us.dontcareabout.PushAsInput.client.component.SingaporeGrid;
import us.dontcareabout.PushAsInput.client.data.DataReadyEvent;
import us.dontcareabout.PushAsInput.client.data.DataReadyEvent.DataReadyHandler;
import us.dontcareabout.PushAsInput.shared.ContentParse;
import us.dontcareabout.PushAsInput.shared.RawPush;
import us.dontcareabout.PushAsInput.shared.Singapore;

public class SingaporeUI extends Composite {
	private static SingaporeUIUiBinder uiBinder = GWT.create(SingaporeUIUiBinder.class);
	interface SingaporeUIUiBinder extends UiBinder<Widget, SingaporeUI> {}

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
		data.clear();
		HashMap<String, Singapore> map = new HashMap<>();

		for (RawPush rp : rawData) {
			Singapore tupple = ContentParse.singapore(rp.getContent());

			if (tupple == null) { continue; }

			tupple.setUserId(rp.getId());
			Singapore old = map.put(tupple.getUserId(), tupple);

			if (old != null) { data.remove(old); }

			data.add(tupple);
		}

		grid.setData(data);
		locationList.setData(data);
	}
}
