package us.dontcareabout.PushAsInput.shared;

import java.util.Date;

public class ContentParse {
	public static Singapore singapore(String content) {
		String[] tokens = content.split(" ");
		if (tokens.length < 3 || tokens.length > 4) {
			return null;
		}

		try {
			Singapore result = new Singapore();
			result.setAmount(Integer.parseInt(tokens[0]));
			result.setLocation(tokens[1]);
			result.setStyle(tokens[2]);

			if (tokens.length == 4) {
				result.setDeadline(new Date(tokens[3]));
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
