package us.dontcareabout.PushAsInput.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.container.Viewport;

import us.dontcareabout.PushAsInput.client.data.DataReadyEvent;
import us.dontcareabout.PushAsInput.client.data.DataReadyEvent.DataReadyHandler;
import us.dontcareabout.PushAsInput.client.ui.SingaporeUI;
import us.dontcareabout.PushAsInput.client.vo.RawPushJS;
import us.dontcareabout.PushAsInput.shared.RawPush;
import us.dontcareabout.gwt.client.GFEP;

public class PushAsInputEP extends GFEP {
	static final SimpleEventBus eventBus = new SimpleEventBus();
	static final RpcServiceAsync rpc = GWT.create(RpcService.class);

	Viewport vp = new Viewport();

	public PushAsInputEP() {
		vp.add(new SingaporeUI());
		RootPanel.get().add(vp);
	}

	@Override
	protected String version() { return "0.0.1"; }

	@Override
	protected String defaultLocale() { return "zh_TW"; }

	@Override
	protected void featureFail() {
		Window.alert("這個瀏覽器我不尬意，不給用..... \\囧/");
	}

	@Override
	protected void start() {
		vp.mask("資料讀取中...");
		fetchByRequest();
	}

	private void fetchByRPC() {
		String url = "https://www.ptt.cc/bbs/Singapore/M.1535806897.A.57F.html";
		rpc.pushList(url, new AsyncCallback<ArrayList<RawPush>>() {
			@Override
			public void onSuccess(ArrayList<RawPush> result) {
				vp.unmask();
				eventBus.fireEvent(new DataReadyEvent(result));
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void fetchByRequest() {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() + "Singapore.json");

		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					ArrayList<RawPush> result = new ArrayList<>();
					JsArray<RawPushJS> data = JsonUtils.<JsArray<RawPushJS>>safeEval(response.getText());

					for (int i = 0; i < data.length(); i++) {
						//必須繞一層，不然泛型會導致 JSNI casting 失敗
						RawPushJS rpjs = data.get(i);
						result.add(rpjs);
					}

					vp.unmask();
					eventBus.fireEvent(new DataReadyEvent(result));
				}

				@Override
				public void onError(Request request, Throwable exception) {
				}
			});
		} catch(Exception e) {
		}
	}

	public static HandlerRegistration addDataReady(DataReadyHandler handler) {
		return eventBus.addHandler(DataReadyEvent.TYPE, handler);
	}
}
