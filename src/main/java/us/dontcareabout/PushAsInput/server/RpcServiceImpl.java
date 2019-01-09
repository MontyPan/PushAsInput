package us.dontcareabout.PushAsInput.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import us.dontcareabout.PushAsInput.client.RpcService;
import us.dontcareabout.PushAsInput.shared.RawPush;

public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {
	private static final long serialVersionUID = 1L;

	@Override
	public ArrayList<RawPush> pushList(String url) {
		return Fetch.process(url);
	}
}
