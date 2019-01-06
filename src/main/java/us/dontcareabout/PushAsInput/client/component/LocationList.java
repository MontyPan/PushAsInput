package us.dontcareabout.PushAsInput.client.component;

import java.util.ArrayList;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;

import us.dontcareabout.PushAsInput.shared.Singapore;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;

public class LocationList extends LayerContainer {
	public void setData(ArrayList<Singapore> data) {
		this.clear();

		for (Singapore loc : data) {
			Button btn = new Button(loc.getLocation());
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
		Button(String text) {
			setBgColor(RGB.DARKGRAY);
			setTextColor(RGB.WHITE);
			setBgRadius(10);
			setText(text);

			addSpriteSelectionHandler(new SpriteSelectionHandler() {
				@Override
				public void onSpriteSelect(SpriteSelectionEvent event) {
					// TODO Auto-generated method stub
				}
			});
		}
	}
}
