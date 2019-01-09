package us.dontcareabout.PushAsInput.client.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;

import us.dontcareabout.PushAsInput.client.ui.SingaporeUI;
import us.dontcareabout.PushAsInput.shared.Singapore;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;

public class LocationList extends LayerContainer {
	public void setData(ArrayList<Singapore> data) {
		this.clear();

		HashMap<String, Integer> map = new HashMap<>();

		for (Singapore sg : data) {
			Integer counter = map.get(sg.getLocation());
			if (counter == null) {
				map.put(sg.getLocation(), 1);
			} else {
				map.put(sg.getLocation(), counter + 1);
			}
		}

		ArrayList<String> location = new ArrayList<>(map.keySet());
		Collections.sort(location);

		for (String loc : location) {
			Button btn = new Button(loc, map.get(loc));
			this.addLayer(btn);
			btn.redraw(true);
		}

		onResize(getOffsetWidth(), getOffsetHeight());
	}

	@Override
	protected void onResize(int width, int height) {
		int offset = 2;

		for (LayerSprite ls : getLayers()) {
			ls.resize(width - 20, 32);
			ls.setLX(2);
			ls.setLY(offset);
			offset += 34;
		}

		super.onResize(width, height);
	}

	class Button extends TextButton {
		boolean select = false;

		Button(final String text, int amount) {
			changeColor();
			setBgRadius(10);
			setText(text + " (" + amount + ")");

			addSpriteSelectionHandler(new SpriteSelectionHandler() {
				@Override
				public void onSpriteSelect(SpriteSelectionEvent event) {
					select = !select;
					SingaporeUI.locationChange(text, select);
					changeColor();
				}
			});
		}

		private void changeColor() {
			setTextColor(select ? RGB.BLACK : RGB.WHITE);
			setBgColor(select ? RGB.RED : RGB.BLACK);
		}
	}
}
