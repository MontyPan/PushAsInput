package us.dontcareabout.PushAsInput.client.component;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import us.dontcareabout.PushAsInput.shared.Singapore;
import us.dontcareabout.gxt.client.model.NoExceptionValueProvider;

public class SingaporeGrid extends Grid<Singapore> {
	static final Properties p = GWT.create(Properties.class);
	static final DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy/MM/dd");

	public SingaporeGrid() {
		super(new ListStore<>(p.id()), genCM());
		getView().setForceFit(true);
	}

	public void setData(ArrayList<Singapore> data) {
		store.clear();
		store.addAll(data);
	}

	private static ColumnModel<Singapore> genCM() {
		ArrayList<ColumnConfig<Singapore, ?>> result = new ArrayList<>();
		result.add(new ColumnConfig<>(p.userId(), 100, "徵求者"));
		result.add(new ColumnConfig<>(p.amount(), 50, "數量"));
		result.add(new ColumnConfig<>(p.location(), 200, "地點"));
		result.add(new ColumnConfig<>(p.style(), 200, "交易方式"));
		result.add(new ColumnConfig<>(new NoExceptionValueProvider<Singapore, String>("") {
			@Override
			protected String extract(Singapore entity) throws Throwable {
				return dateFormat.format(entity.getDeadline());
			}
		}, 100, "截止日期"));
		return new ColumnModel<>(result);
	}

	interface Properties extends PropertyAccess<Singapore> {
		@Path("userId")
		ModelKeyProvider<Singapore> id();

		ValueProvider<Singapore, String> userId();
		ValueProvider<Singapore, Integer> amount();
		ValueProvider<Singapore, String> location();
		ValueProvider<Singapore, String> style();
	}
}
