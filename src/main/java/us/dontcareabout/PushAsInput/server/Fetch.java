package us.dontcareabout.PushAsInput.server;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import us.dontcareabout.PushAsInput.shared.RawPush;

public class Fetch {
	/**
	 * @return 指定 URL 下方的推文，轉成 {@link RawPush} 的 {@link ArrayList}
	 */
	public static ArrayList<RawPush> process(String url) {
		ArrayList<RawPush> result = new ArrayList<>();
		Document doc;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			return result;
		}

		for (Element e : doc.select(".push")) {
			RawPush rp = new RawPush();
			rp.setType(e.select(".push-tag").text());
			rp.setId(e.select(".push-userid").text());
			rp.setContent(e.select(".push-content").text());
			result.add(rp);
		}

		return result;
	}
}
