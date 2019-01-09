package us.dontcareabout.PushAsInput.client.component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.StoreFilter;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import us.dontcareabout.PushAsInput.shared.Singapore;
import us.dontcareabout.gxt.client.model.NoExceptionValueProvider;

public class SingaporeGrid extends Grid<Singapore> {
	static final Properties p = GWT.create(Properties.class);
	static final DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy/MM/dd");

	private StoreFilter<Singapore> filter = new StoreFilter<Singapore>() {
		private Date now = new Date();

		@Override
		public boolean select(Store<Singapore> store, Singapore parent, Singapore item) {
			if (item.getDeadline() == null) { return true; }
			return item.getDeadline().getTime() > now.getTime();
		}
	};

	public SingaporeGrid() {
		super(new ListStore<>(p.id()), genCM());
		getView().setForceFit(true);

		store.addSortInfo(new StoreSortInfo<>(new Comparator<Singapore>() {
			@Override
			public int compare(Singapore o1, Singapore o2) {

				if (o1.getDeadline() == null && o2.getDeadline() == null) { return 0; }
				if (o1.getDeadline() == null) { return 1; }
				if (o2.getDeadline() == null) { return -1; }
				return o1.getDeadline().compareTo(o2.getDeadline());
			}
		}, SortDir.ASC));
		store.addFilter(filter);
		store.setEnableFilters(true);
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
