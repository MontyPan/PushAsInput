package us.dontcareabout.PushAsInput.server;

import us.dontcareabout.java.common.DoubleProperties;

public class Setting extends DoubleProperties {
	private static String localRepo;
	private static String username;
	private static String password;

	public Setting() {
		super("dev-setting.xml", "PushAsInput.xml");
	}

	public String repo() {
		if (localRepo == null) {
			localRepo = getProperty("localRepo");
		}

		return localRepo;
	}

	public String username() {
		if (username == null) {
			username = getProperty("username");
		}

		return username;
	}

	public String password() {
		if (password == null) {
			password = getProperty("password");
		}

		return password;
	}
}
