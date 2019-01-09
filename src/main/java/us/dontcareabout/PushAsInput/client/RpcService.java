package us.dontcareabout.PushAsInput.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import us.dontcareabout.PushAsInput.shared.RawPush;

@RemoteServiceRelativePath("RPC")
public interface RpcService extends RemoteService{
	ArrayList<RawPush> pushList(String url);
}
